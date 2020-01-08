package com.vnaskos.ids.adapter.influxdb;

import org.influxdb.dto.Point;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TemplatePoint {

    private final String measurementName;
    private final Long time;
    private final Fields fields;
    private final Tags tags;

    public TemplatePoint(String measurementName, Long time, Fields fields, Tags tags) {
        this.measurementName = measurementName;
        this.time = time;
        this.fields = fields == null ? new Fields() : fields;
        this.tags = tags == null ? new Tags() : tags;
    }

    public String getMeasurementName() {
        return measurementName;
    }

    public Long getTime() {
        return time;
    }

    public Fields getFields() {
        final Fields copyOfFields = new Fields();
        copyOfFields.addAll(fields);
        return copyOfFields;
    }

    public Tags getTags() {
        Tags copyOfTags = new Tags();
        copyOfTags.addAll(tags);
        return copyOfTags;
    }

    public Point toPoint() {
        Point.Builder pointBuilder = Point.measurement(measurementName)
                .fields(fields.stream().collect(Collectors.toMap(Field::getName, Field::getValue)))
                .tag(tags.stream().collect(Collectors.toMap(Tag::getName, Tag::getValue)));

        if (time != null) {
            pointBuilder.time(time, TimeUnit.MILLISECONDS);
        }

        return pointBuilder.build();
    }

    public TemplatePoint resolved() {
        Fields resolvedFields = Fields.of(
                fields.stream()
                        .map(field -> new Field(field.getName(), field.resolve()))
                        .toArray(Field[]::new));

        return new TemplatePoint(
                getMeasurementName(),
                getTime(),
                resolvedFields,
                getTags());
    }
}
