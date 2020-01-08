package com.vnaskos.ids.provider.config;

import java.io.Serializable;

public class InfluxTaskConfigurationModel implements Serializable {

    private String host;
    private String database;
    private String username;
    private String password;
    private String measurement;

    private String mqttHost;
    private String mqttPort;
    private String mqttTopic;

    private Integer fetchDataPeriodicallyEvery;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public Integer getFetchDataPeriodicallyEvery() {
        return fetchDataPeriodicallyEvery;
    }

    public void setFetchDataPeriodicallyEvery(Integer fetchDataPeriodicallyEvery) {
        this.fetchDataPeriodicallyEvery = fetchDataPeriodicallyEvery;
    }

    public String getMqttHost() {
        return mqttHost;
    }

    public void setMqttHost(String mqttHost) {
        this.mqttHost = mqttHost;
    }

    public String getMqttPort() {
        return mqttPort;
    }

    public void setMqttPort(String mqttPort) {
        this.mqttPort = mqttPort;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(String mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public InfluxDbConfigurationModel toInfluxDbConfigurationModel() {
        final InfluxDbConfigurationModel model = new InfluxDbConfigurationModel();
        model.setHost(this.host);
        model.setDatabase(this.database);
        model.setUsername(this.username);
        model.setPassword(this.password);
        model.setMeasurement(this.measurement);
        return model;
    }
}
