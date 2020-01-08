package com.vnaskos.adapter.dao;

import com.vnaskos.adapter.dto.TemperaturePoint;
import org.influxdb.InfluxDB;
import org.influxdb.dto.BoundParameterQuery;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TemperatureDAOImpl implements TemperatureDAO {

    @Value("${spring.influx.dbname}")
    private String databaseName;

    private InfluxDB influxdb;

    @Autowired
    public TemperatureDAOImpl(final InfluxDB influxdb) {
        this.influxdb = influxdb;
    }

    @Override
    public List<TemperaturePoint> getTemperatureMeasurements() {
        final Query query = BoundParameterQuery.QueryBuilder
                .newQuery("SELECT * FROM temperature")
                .forDatabase(databaseName)
                .create();

        final QueryResult result = influxdb.query(query);
        final InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        return resultMapper.toPOJO(result, TemperaturePoint.class);
    }

    public TemperaturePoint getLastMeasurement() {
        final Query query = BoundParameterQuery.QueryBuilder
                .newQuery("SELECT * FROM temperature ORDER BY time DESC LIMIT 1")
                .forDatabase(databaseName)
                .create();

        final QueryResult result = influxdb.query(query);
        final InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        return resultMapper.toPOJO(result, TemperaturePoint.class).get(0);
    }
}
