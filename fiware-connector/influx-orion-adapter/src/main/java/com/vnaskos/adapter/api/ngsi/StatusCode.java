package com.vnaskos.adapter.api.ngsi;

import java.io.Serializable;

public class StatusCode implements Serializable {

    private String code = "200";

    private String reasonPhrase = "OK";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }
}
