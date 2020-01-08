package com.vnaskos.ids.provider;

import com.vnaskos.ids.adapter.influxdb.service.InfluxDBCommonIOManager;
import com.vnaskos.ids.provider.config.MqttIntegrationConfiguration.MqttGateway;
import com.vnaskos.ids.provider.config.ProviderConfigurationProperties;
import com.vnaskos.ids.provider.task.InfluxDbMqttProviderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MqttSensorReadingScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(MqttSensorReadingScheduler.class);

    private final ProviderConfigurationProperties config;
    private final MqttGateway mqttGateway;
    private final InfluxDBCommonIOManager influxDBCommonIOManager;
    private final ScheduleTaskService scheduleTaskService;

    private int taskIdGenerator = 1000;

    @Autowired
    public MqttSensorReadingScheduler(final ProviderConfigurationProperties config,
                                      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                          final MqttGateway mqttGateway,
                                      final InfluxDBCommonIOManager influxDBCommonIOManager,
                                      final ScheduleTaskService scheduleTaskService) {
        this.config = config;
        this.mqttGateway = mqttGateway;
        this.influxDBCommonIOManager = influxDBCommonIOManager;
        this.scheduleTaskService = scheduleTaskService;
    }

    @GetMapping(path = "/provider/status")
    public ResponseEntity isRunning() {
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("running_tasks", scheduleTaskService.getRunningTaskIds().stream()
                .map(id -> Integer.toString(id))
                .collect(Collectors.joining(",")));
        return ResponseEntity.ok(jsonBody);
    }

    @GetMapping(path = "/provider/start/influx")
    public ResponseEntity startInfluxProvider() {
        int newTaskId = taskIdGenerator++;

        scheduleTaskService.addTaskToScheduler(newTaskId,
                new InfluxDbMqttProviderTask(mqttGateway, influxDBCommonIOManager,
                        config.getMqttTopic(),
                        config.getInfluxConfig().getDatabase(),
                        config.getInfluxConfig().getMeasurement(),
                        config.getFetchDataPeriodicallyEvery()));

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("running", "true");
        jsonBody.put("task_id", newTaskId+"");

        return ResponseEntity.ok(jsonBody);
    }

    @GetMapping(path = "/provider/stop/{taskId}")
    public ResponseEntity stopProvider(@PathVariable Integer taskId) {
        scheduleTaskService.removeTaskFromScheduler(taskId);
        LOG.info("Provider stop triggered for task "+taskId+"!");
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("stopped", taskId+"");
        jsonBody.put("msg", "Provider stop triggered for task "+taskId+"!");
        return ResponseEntity.ok(jsonBody);
    }
}
