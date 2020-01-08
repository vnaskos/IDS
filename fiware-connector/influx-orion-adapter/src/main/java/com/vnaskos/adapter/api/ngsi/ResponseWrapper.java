package com.vnaskos.adapter.api.ngsi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseWrapper implements Serializable {

    private List<ContextResponse> contextResponses = new ArrayList<>();

    public List<ContextResponse> getContextResponses() {
        return contextResponses;
    }

    public void addContextResponse(final ContextResponse contextResponse) {
        this.contextResponses.add(contextResponse);
    }
}
