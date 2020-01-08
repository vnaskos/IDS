package com.vnaskos.adapter.api.ngsi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContextElement implements Serializable {

    private List<ContextAttribute> attributes = new ArrayList<>();

    private String id;

    private String isPattern = Boolean.FALSE.toString();

    private String type;

    public List<ContextAttribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(final ContextAttribute contextAttribute) {
        attributes.add(contextAttribute);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsPattern() {
        return isPattern;
    }

    public void setPattern(Boolean isPattern) {
        this.isPattern = isPattern.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
