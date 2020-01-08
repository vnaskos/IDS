package com.vnaskos.adapter.dto;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "temperature")
public class TemperaturePoint {

    @Column(name = "time")
    private Instant time;

    @Column(name = "name")
    private String name;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "value")
    private String value;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
