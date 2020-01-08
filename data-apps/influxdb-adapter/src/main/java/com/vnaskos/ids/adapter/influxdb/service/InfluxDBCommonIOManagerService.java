package com.vnaskos.ids.adapter.influxdb.service;

import com.vnaskos.ids.adapter.influxdb.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;

public class InfluxDBCommonIOManagerService implements InfluxDBCommonIOManager {

    public static final String NO_USERNAME = "";
    public static final String NO_PASSWORD = "";

    private InfluxDB influxDB;

    @Override
    public void connect(String databaseURL, String username, String password) {
        boolean ssl = true;
        if (influxDB != null) {
            this.influxDB.close();
            this.influxDB = null;
        }

        if (username.isEmpty() || password.isEmpty()) {
            this.influxDB = InfluxDBFactory.connect(databaseURL);
        } else {
            this.influxDB = InfluxDBFactory.connect(databaseURL, username, password);
        }
        OkHttpClient.Builder builder = null;
        if (ssl) {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
                };
                
                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                
                builder = new OkHttpClient.Builder()
                        .sslSocketFactory(sc.getSocketFactory(), (X509TrustManager) trustAllCerts[0]).readTimeout(100, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(InfluxDBCommonIOManagerService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (KeyManagementException ex) {
                Logger.getLogger(InfluxDBCommonIOManagerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            builder = new OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS).connectTimeout(1000, TimeUnit.SECONDS);
        }

        if (username.isEmpty() || password.isEmpty()) {
            this.influxDB = InfluxDBFactory.connect(databaseURL, builder);
        } else {
            this.influxDB = InfluxDBFactory.connect(databaseURL, username, password, builder);
        }
    }

    @Override
    public List<TemplatePoint> query(String database, String query) {
        Query customQuery = BoundParameterQuery.QueryBuilder.newQuery(query)
                .forDatabase(database)
                .create();

        QueryResult queryResult = influxDB.query(customQuery);
        if (queryResult == null || queryResult.hasError() || queryResult.getResults().isEmpty()) {
            return Collections.emptyList();
        }

        return getResultMeasurements(queryResult);
    }

    @Override
    public List<TemplatePointResponseModel> readLatestMeasurements(Integer numberOfResults, String databaseName, String measurementName) {
        Query lastNMeasurements = BoundParameterQuery.QueryBuilder.newQuery(
                "SELECT * FROM "+measurementName+" WHERE time > now() - "+numberOfResults+"s AND time <= now()")
                .forDatabase(databaseName)
                .create();

        QueryResult queryResult = influxDB.query(lastNMeasurements);
        if (queryResult == null || queryResult.hasError() || queryResult.getResults().isEmpty()) {
            return Collections.emptyList();
        }

        return getResultMeasurements(queryResult).stream().map(TemplatePointResponseModel::from).collect(Collectors.toList());
    }

    private List<TemplatePoint> getResultMeasurements(QueryResult queryResult) {
        final List<QueryResult.Series> resultSeries = queryResult.getResults().get(0).getSeries();

        if (resultSeries == null || resultSeries.isEmpty()) {
            return Collections.emptyList();
        }

        final List<TemplatePoint> measurements = new ArrayList<>();

        for (QueryResult.Series series : resultSeries) {
            measurements.addAll(getSeriesMeasurements(series));
        }

        return measurements;
    }

    private List<TemplatePoint> getSeriesMeasurements(QueryResult.Series series) {
        final List<TemplatePoint> seriesMeasurements = new ArrayList<>();
        final List<String> fieldNames = series.getColumns();
        final Tags tags = getTags(series);
        final String measurementName = series.getName();

        for (List<Object> fieldValues : series.getValues()) {
            final Fields fields = getFields(fieldNames, fieldValues);
            final Long time = Instant.parse(
                    fields.remove("time").getValue().toString()).toEpochMilli();

            seriesMeasurements.add(new TemplatePoint(measurementName, time, fields, tags));
        }
        return seriesMeasurements;
    }

    private Fields getFields(List<String> fieldNames, List<Object> fieldValues) {
        final Fields fields = new Fields();
        for (int i=0; i<fieldValues.size(); i++) {
            Object fieldValue = fieldValues.get(i);
            fields.add(new Field(fieldNames.get(i), fieldValue));
        }
        return fields;
    }

    private Tags getTags(QueryResult.Series series) {
        if (series.getTags() == null) {
            return new Tags();
        }

        return Tags.of(series.getTags().entrySet().stream()
                        .map(entry -> new Tag(entry.getKey(), entry.getValue()))
                        .toArray(Tag[]::new));
    }

    @Override
    public TemplatePoint write(String databaseName, TemplatePoint templatePoint) {
        final TemplatePoint resolvedPoint = templatePoint.resolved();
        final Point point = resolvedPoint.toPoint();

        influxDB.setDatabase(databaseName);
        influxDB.write(point);
        return resolvedPoint;
    }
}
