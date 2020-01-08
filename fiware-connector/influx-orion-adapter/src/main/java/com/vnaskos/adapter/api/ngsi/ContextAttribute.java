package com.vnaskos.adapter.api.ngsi;

import java.io.Serializable;

public class ContextAttribute implements Serializable {

    private final String name;
    private final String type;
    private final String value;

    protected ContextAttribute(final String name, final String type, final String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public static ContextAttribute create(final String name, final String type, final String value) {
        return new ContextAttribute(name, type, value);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
