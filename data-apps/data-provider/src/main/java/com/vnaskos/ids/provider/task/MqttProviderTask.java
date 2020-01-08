package com.vnaskos.ids.provider.task;

import com.vnaskos.ids.provider.config.MqttIntegrationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

abstract class MqttProviderTask implements ProviderTask {

    private static final Logger LOG = LoggerFactory.getLogger(MqttProviderTask.class);

    private final MqttIntegrationConfiguration.MqttGateway mqttGateway;
    private final String topic;

    MqttProviderTask(MqttIntegrationConfiguration.MqttGateway mqttGateway, String topic) {
        this.mqttGateway = mqttGateway;
        this.topic = topic;
    }

    @Override
    public void run() {
        try {
            String data = getData();
            publishToMqtt(data);
        } catch (Exception e) {
            LOG.error("[PROVIDER] data were not sent");
        }
    }

    abstract String getData() throws Exception;

    private void publishToMqtt(String jsonMeasurements) {
        LOG.info("[PROVIDER] send data to mqtt");
        LOG.debug(jsonMeasurements);

        Message<String> msg = MessageBuilder.withPayload(jsonMeasurements)
                .setHeader("mqtt_topic", topic)
                .build();

        mqttGateway.send(msg);
    }
}
