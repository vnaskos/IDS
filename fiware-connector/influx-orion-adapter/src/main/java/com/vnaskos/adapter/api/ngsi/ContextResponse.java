package com.vnaskos.adapter.api.ngsi;

import java.io.Serializable;

public class ContextResponse implements Serializable {

    private ContextElement contextElement;

    private StatusCode statusCode;

    public ContextElement getContextElement() {
        return contextElement;
    }

    public void setContextElement(ContextElement contextElement) {
        this.contextElement = contextElement;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
