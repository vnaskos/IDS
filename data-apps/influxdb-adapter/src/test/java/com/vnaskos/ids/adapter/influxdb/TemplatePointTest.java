package com.vnaskos.ids.adapter.influxdb;

import org.influxdb.dto.Point;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplatePointTest {

    private static final Long A_TIME = null;
    private static final Fields NO_FIELDS = null;
    private static final Tags NO_TAGS = null;
    private static final String RAND_FLOAT_100_200 = "${randFloat,100,200}";

    @Test
    public void fieldsShouldBeImmutable() {
        Fields fields = Fields.of(new Field("FIELD_NAME", "FIELD_VALUE"));
        TemplatePoint templatePoint = new TemplatePoint("NAME", A_TIME, fields, NO_TAGS);

        Fields copyOfFields = templatePoint.getFields();

        copyOfFields.add(new Field("ANOTHER_FIELD_NAME", "FIELD_VALUE"));
        assertThat(fields).isNotEqualTo(copyOfFields);
        assertThat(fields).hasSize(1);
    }

    @Test
    public void tagsShouldBeImmutable() {
        Tags tags = Tags.of(new Tag("TAG_NAME", "TAG_VALUE"));
        TemplatePoint templatePoint = new TemplatePoint("NAME", A_TIME, NO_FIELDS, tags);

        Tags copyOfTags = templatePoint.getTags();

        copyOfTags.add(new Tag("ANOTHER_TAG_NAME", "TAG_VALUE"));
        assertThat(tags).isNotEqualTo(copyOfTags);
        assertThat(tags).hasSize(1);
    }

    @Test
    public void useEmptyTagsAndFieldsWhenNoneOfThemAreGiven() {
        TemplatePoint templatePoint = new TemplatePoint("NAME", A_TIME, NO_FIELDS, NO_TAGS);

        assertThat(templatePoint.getFields()).isNotNull();
        assertThat(templatePoint.getTags()).isNotNull();
    }

    @Test
    public void templatePointCanBeConvertedToValidInfluxdbPoint() {
        String name = "measurementName";
        long time = System.currentTimeMillis();
        Field field = new Field("FIELD_NAME", "FIELD_VALUE");
        Tag tag = new Tag("TAG_NAME", "TAG_VALUE");
        TemplatePoint templatePoint = new TemplatePoint(name, time, Fields.of(field), Tags.of(tag));

        Point expectedPoint = Point.measurement(name)
                .time(time, TimeUnit.MILLISECONDS)
                .addField(field.getName(), field.getValue().toString())
                .tag(tag.getName(), tag.getValue())
                .build();

        Point actualPoint = templatePoint.toPoint();

        assertThat(expectedPoint.lineProtocol()).isEqualTo(actualPoint.lineProtocol());
    }

    @Test
    public void resolveIsReturningACopyTemplatePointWithResolvedFields() {
        String name = "measurementName";
        long time = System.currentTimeMillis();
        Field randomFloatField = new Field(Field.Type.RANDOM_FLOAT,"RANDOM_FLOAT", RAND_FLOAT_100_200);
        Fields singleField = Fields.of(randomFloatField);
        Tags tags = Tags.of(new Tag("TAG_NAME", "TAG_VALUE"));
        TemplatePoint templatePoint = new TemplatePoint(name, time, singleField, tags);

        TemplatePoint resolved = templatePoint.resolved();

        assertThat(randomFloatField.getValue()).isEqualTo(RAND_FLOAT_100_200);
        assertThat(resolved.getFields()).hasSize(singleField.size());
        assertThat(resolved.getFields().iterator().next().getValue()).isNotEqualTo(RAND_FLOAT_100_200);
    }
}