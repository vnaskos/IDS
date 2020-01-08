package com.vnaskos.ids.provider;

import com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManager;
import com.vnaskos.ids.provider.config.MqttIntegrationConfiguration;
import com.vnaskos.ids.provider.config.ProviderConfigurationProperties;
import org.junit.Before;
import org.mockito.Mockito;

public class MqttSensorReadingSchedulerTest {

    private final MqttIntegrationConfiguration.MqttGateway gateway =
            Mockito.mock(MqttIntegrationConfiguration.MqttGateway.class);

    private MqttSensorReadingScheduler mqttSensorReadingScheduler;

    @Before
    public void setup() {
        final ProviderConfigurationProperties config = Mockito.mock(ProviderConfigurationProperties.class);
        final InfluxDBCommonIOManager influxManager = Mockito.mock(InfluxDBCommonIOManager.class);
        final ScheduleTaskService scheduleTaskService = Mockito.mock(ScheduleTaskService.class);

        this.mqttSensorReadingScheduler = Mockito.spy(new MqttSensorReadingScheduler(config, gateway, influxManager, scheduleTaskService));
    }

//    @Test
//    public void scheduleTaskOnStartEndpoint() {
//        Assert.assertEquals(ResponseEntity.ok(false), mqttSensorReadingScheduler.isRunning());
//        mqttSensorReadingScheduler.startInfluxProvider();
//        Assert.assertEquals(ResponseEntity.ok(true), mqttSensorReadingScheduler.isRunning());
//    }
//
//    @Test
//    public void removeScheduledTaskOnStopEndpoint() {
//        mqttSensorReadingScheduler.startInfluxProvider();
//        Assert.assertEquals(ResponseEntity.ok(true), mqttSensorReadingScheduler.isRunning());
//        mqttSensorReadingScheduler.stopProvider(1000);
//        Assert.assertEquals(ResponseEntity.ok(false), mqttSensorReadingScheduler.isRunning());
//    }
//
//    @Test
//    public void doNothingOnStopEndpointWhenThereIsNoScheduledTask() {
//        Assert.assertEquals(ResponseEntity.ok(false), mqttSensorReadingScheduler.isRunning());
//        mqttSensorReadingScheduler.stopProvider(1000);
//        Assert.assertEquals(ResponseEntity.ok(false), mqttSensorReadingScheduler.isRunning());
//    }
}
