package com.vnaskos.ids.provider.task;

import com.vnaskos.ids.adapter.influxdb.TemplatePoint;
import com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManager;
import com.vnaskos.ids.provider.MeasurementsResponseModel;
import com.vnaskos.ids.provider.config.MqttIntegrationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InfluxDbMqttProviderTask extends MqttProviderTask {

    private static final Logger LOG = LoggerFactory.getLogger(InfluxDbMqttProviderTask.class);

    private final String database;
    private final String measurement;
    private final Integer fetchDataPeriodicallyEvery;

    private final InfluxDBCommonIOManager influxDBCommonIOManager;

    public InfluxDbMqttProviderTask(
            final MqttIntegrationConfiguration.MqttGateway mqttGateway,
            final InfluxDBCommonIOManager influxDBCommonIOManager, final String mqttTopic,
            final String database, final String measurement, final Integer fetchDataPeriodicallyEvery) {
        super(mqttGateway, mqttTopic);
        this.influxDBCommonIOManager = influxDBCommonIOManager;
        this.database = database;
        this.measurement = measurement;
        this.fetchDataPeriodicallyEvery = fetchDataPeriodicallyEvery;
    }

    @Override
    public String getData() {
        LOG.info("[PROVIDER] get latest measurements");

        List<TemplatePoint> measurements = influxDBCommonIOManager.query(
                database,
                String.format("SELECT mean(value) as value FROM %s WHERE time > now() - %ds AND time <= now() GROUP BY time(1s) fill(none)",
                        measurement, fetchDataPeriodicallyEvery));

        return MeasurementsResponseModel.asJsonFrom(measurements);
    }
}
