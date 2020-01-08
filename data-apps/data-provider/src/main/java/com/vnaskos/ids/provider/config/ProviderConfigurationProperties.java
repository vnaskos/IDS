package com.vnaskos.ids.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.vnaskos.ids.provider")
public class ProviderConfigurationProperties {

    private String mqttHost;
    private String mqttPort;
    private String mqttTopic;

    private InfluxDbConfigurationModel influxConfig;

    private Integer fetchDataPeriodicallyEvery;

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

    public InfluxDbConfigurationModel getInfluxConfig() {
        return influxConfig;
    }

    public void setInfluxConfig(InfluxDbConfigurationModel influxConfig) {
        this.influxConfig = influxConfig;
    }

    public Integer getFetchDataPeriodicallyEvery() {
        return fetchDataPeriodicallyEvery;
    }

    public void setFetchDataPeriodicallyEvery(Integer fetchDataPeriodicallyEvery) {
        this.fetchDataPeriodicallyEvery = fetchDataPeriodicallyEvery;
    }
}