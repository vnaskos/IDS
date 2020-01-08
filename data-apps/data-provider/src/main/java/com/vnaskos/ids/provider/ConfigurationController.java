package com.vnaskos.ids.provider;

import com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManager;
import com.vnaskos.ids.provider.config.InfluxTaskConfigurationModel;
import com.vnaskos.ids.provider.config.ProviderConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationController.class);

    private ProviderConfigurationProperties config;

    private InfluxDBCommonIOManager influxDBCommonIOManager;

    @Autowired
    public ConfigurationController(ProviderConfigurationProperties config, InfluxDBCommonIOManager influxDBCommonIOManager) {
        this.config = config;
        this.influxDBCommonIOManager = influxDBCommonIOManager;
    }

    @GetMapping(path = "/config/influxdb")
    public ResponseEntity getCurrentInfluxDbConfiguration() {
        InfluxTaskConfigurationModel model = new InfluxTaskConfigurationModel();
        model.setHost(config.getInfluxConfig().getHost());
        model.setDatabase(config.getInfluxConfig().getDatabase());
        model.setUsername(config.getInfluxConfig().getUsername());
        model.setPassword(config.getInfluxConfig().getPassword());
        model.setMeasurement(config.getInfluxConfig().getMeasurement());
        model.setFetchDataPeriodicallyEvery(config.getFetchDataPeriodicallyEvery());
        model.setMqttHost(config.getMqttHost());
        model.setMqttPort(config.getMqttPort());
        model.setMqttTopic(config.getMqttTopic());
        return ResponseEntity.ok().body(model);
    }

    @PostMapping(path = "/config/influxdb")
    public ResponseEntity configInfluxDb(
            @RequestBody InfluxTaskConfigurationModel configModel) {

        config.setFetchDataPeriodicallyEvery(configModel.getFetchDataPeriodicallyEvery());
        config.setInfluxConfig(configModel.toInfluxDbConfigurationModel());
        config.setMqttHost(configModel.getMqttHost());
        config.setMqttPort(configModel.getMqttPort());
        config.setMqttTopic(configModel.getMqttTopic());

        try {
            influxDBCommonIOManager.connect(configModel.getHost(), configModel.getUsername(), configModel.getPassword());
        } catch (Exception ex) {
            LOG.error("Couldn't establish connection with influxDB");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't establish connection with influxDB");
        }

        return ResponseEntity.ok(configModel);
    }
}