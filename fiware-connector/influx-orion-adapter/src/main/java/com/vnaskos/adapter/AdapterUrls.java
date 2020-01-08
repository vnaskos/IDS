package com.vnaskos.adapter;

import com.vnaskos.adapter.api.UrlSpace;

public class AdapterUrls implements UrlSpace {

    public final static String LIST_TEMPERATURE_INFLUX_POINTS = "/v1/temperature/list";

    public final static String ORION_QUERY_TEMPERATURE_CONTEXT = "/v1/temperature/queryContext";

    public final static String UPDATE_SENSOR_TEMPERATURE = "/temperature/sensor/{sensorId}/value/{newTemperature}";
}
