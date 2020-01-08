package com.vnaskos.ids.adapter.influxdb;

import com.vnaskos.ids.adapter.influxdb.resolver.RandomFloatFieldTypeResolver;

import java.util.Objects;

public class Field implements Named {

    private static final RandomFloatFieldTypeResolver RANDOM_FLOAT_RESOLVER = new RandomFloatFieldTypeResolver();

    public enum Type {
        FIXED_VALUE, RANDOM_FLOAT
    }

    private final Type type;
    private final String name;
    private final Object value;

    public Field(String name, Object value) {
        this(Type.FIXED_VALUE, name, value);
    }

    public Field(Type type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Object resolve() {
        if (type == Type.FIXED_VALUE) {
            return getValue();
        } else if (type == Type.RANDOM_FLOAT) {
            return RANDOM_FLOAT_RESOLVER.resolve(getValue());
        }

        throw new UnsupportedOperationException("Field Type can't be recognized!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return name.equals(field.name) &&
                value.equals(field.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}

