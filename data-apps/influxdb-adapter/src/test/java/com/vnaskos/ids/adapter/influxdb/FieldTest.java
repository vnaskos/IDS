package com.vnaskos.ids.adapter.influxdb;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldTest {

    @Test
    public void fixedValueFieldShouldResolveToObjectWithTheSameValue() {
        Field stringField = new Field(Field.Type.FIXED_VALUE, "FIELD_NAME", "FIELD_VALUE");
        assertThat(stringField.resolve()).isEqualTo("FIELD_VALUE");

        Field floatField = new Field(Field.Type.FIXED_VALUE, "FIELD_NAME", 1.5F);
        assertThat(floatField.resolve()).isEqualTo(1.5F);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void resolveThrowsExceptionUnsupportedTypeIsGiven() {
        Field stringField = new Field(null, "FIELD_NAME", "FIELD_VALUE");
        stringField.resolve();

    }
}