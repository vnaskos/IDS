package com.vnaskos.ids.provider.config;

import com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManager;
import com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InfluxDbConnectionManager {

    private static final Logger LOG = LoggerFactory.getLogger(InfluxDbConnectionManager.class);

    private final ProviderConfigurationProperties config;

    @Autowired
    public InfluxDbConnectionManager(ProviderConfigurationProperties config) {
        this.config = config;
    }

    @Bean
    public InfluxDBCommonIOManager influxDbService() {
        final InfluxDBCommonIOManager influxDBCommonIOManager = new InfluxDBCommonIOManagerService();
        try {
            influxDBCommonIOManager.connect(config.getInfluxConfig().getHost(),
                    config.getInfluxConfig().getUsername(),
                    config.getInfluxConfig().getPassword());
        } catch (Exception ex) {
            LOG.error("Couldn't establish connection with influxDB");
        }
        return influxDBCommonIOManager;
    }
}
