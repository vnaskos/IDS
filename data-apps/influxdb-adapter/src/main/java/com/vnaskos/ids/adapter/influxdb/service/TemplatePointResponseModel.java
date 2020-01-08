package com.vnaskos.ids.adapter.influxdb.service;

import com.vnaskos.ids.adapter.influxdb.TemplatePoint;

import java.time.Instant;
import java.util.HashMap;

public class TemplatePointResponseModel extends HashMap<String, Object> {

    public static TemplatePointResponseModel from(TemplatePoint templatePoint) {
        final TemplatePointResponseModel responseModel = new TemplatePointResponseModel();
        responseModel.put("measurement", templatePoint.getMeasurementName());
        responseModel.put("time", Instant.ofEpochMilli(templatePoint.getTime()).toString());
        templatePoint.getFields().forEach(f -> responseModel.put(f.getName(), f.getValue()));
        templatePoint.getTags().forEach(t -> responseModel.put(t.getName(), t.getValue()));
        return responseModel;
    }
}
