package com.vnaskos.ids.adapter.influxdb;

import java.util.Objects;

public class Tag implements Named {

    private final String name;
    private final String value;

    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return name.equals(tag.name) &&
                value.equals(tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
