package com.vnaskos.producer.api.template;

import org.influxdb.dto.Point;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TemplatePoint {

    private final static TemplateFieldParser FIELD_PARSER = new TemplateFieldParser();

    private String measurement;

    private long time = System.currentTimeMillis();

    private Map<String, Object> fields = new HashMap<>();

    private Map<String, String> tags = new HashMap<>();

    public TemplatePoint() { }

    public static TemplatePoint resolved(final TemplatePoint templatePoint) {
        final TemplatePoint resolvedPoint = new TemplatePoint();

        // Resolve any placeholder without mutating the original Map
        resolvedPoint.setFields(templatePoint.getFields().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e-> FIELD_PARSER.eval((String)e.getValue()))));

        resolvedPoint.setTags(Collections.unmodifiableMap(templatePoint.getTags()));

        resolvedPoint.setMeasurement(templatePoint.getMeasurement());
        resolvedPoint.setTime(templatePoint.getTime());

        return resolvedPoint;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Map<String, Object> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public void addField(String key, String val) {
        this.fields.put(key, val);
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public void addTag(String key, String val) {
        this.tags.put(key, val);
    }

    public Point toPoint() {
        return Point.measurement(measurement)
                .time(time, TimeUnit.MILLISECONDS)
                .fields(fields)
                .tag(tags)
                .build();
    }
}
