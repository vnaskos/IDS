package com.vnaskos.consumer.generic;

public class SensorOrionResponse {

    private String id;

    private String type;

    private String description;

    private String refMachine;

    private Double temperature;

    public SensorOrionResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefMachine() {
        return refMachine;
    }

    public void setRefMachine(String refMachine) {
        this.refMachine = refMachine;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
