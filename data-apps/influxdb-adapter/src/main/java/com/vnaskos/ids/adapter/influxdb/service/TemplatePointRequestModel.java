package com.vnaskos.ids.adapter.influxdb.service;

import com.vnaskos.ids.adapter.influxdb.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TemplatePointRequestModel implements Serializable {

    private String measurement;

    private Long time;

    private Map<String, Object> fields = new HashMap<>();

    private Map<String, String> tags = new HashMap<>();

    public TemplatePointRequestModel() {}

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public TemplatePoint toTemplatePoint() {
        final Fields templatePointFields = new Fields();
        for (Map.Entry<String, Object> entry : this.fields.entrySet()) {
            if (entry.getValue().toString().startsWith("${randFloat")) {
                templatePointFields.add(new Field(Field.Type.RANDOM_FLOAT, entry.getKey(), entry.getValue()));
            } else {
                templatePointFields.add(new Field(entry.getKey(), entry.getValue()));
            }
        }

        final Tags templatePointTags = new Tags();
        for (Map.Entry<String, String> entry : this.tags.entrySet()) {
            templatePointTags.add(new Tag(entry.getKey(), entry.getValue()));
        }

        return new TemplatePoint(this.measurement,
                time == null ? System.currentTimeMillis() : time,
                templatePointFields, templatePointTags);
    }
}
