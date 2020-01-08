package com.vnaskos.producer.dummy;

import com.vnaskos.producer.api.template.TemplatePoint;
import org.springframework.stereotype.Service;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    private static final int MAX_TEMP = 100;

    @Override
    public TemplatePoint createRandomTemperaturePoint() {
        final TemplatePoint temperaturePoint = new TemplatePoint();
        temperaturePoint.setMeasurement("temperature");
        temperaturePoint.addField("name", "machine-alpha");
        temperaturePoint.addField("sensor_id", "435282J");
        temperaturePoint.addField("value", String.format("${randFloat,%d}", MAX_TEMP));
        temperaturePoint.addTag("location", "level1");
        return temperaturePoint;
    }
}
