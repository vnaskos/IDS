package com.vnaskos.ids.adapter.influxdb.service;

import com.vnaskos.ids.adapter.influxdb.TemplatePoint;

import java.util.List;

public interface InfluxDBCommonIOManager {

    void connect(String databaseURL, String username, String password);

    List<TemplatePoint> query(String database, String query);

    List<TemplatePointResponseModel> readLatestMeasurements(Integer numberOfResults, String databaseName, String measurementName);

    TemplatePoint write(String databaseName, TemplatePoint templatePoint);
}
