package com.vnaskos.ids.provider;

import com.vnaskos.ids.provider.config.ProviderConfigurationProperties;
import com.vnaskos.ids.provider.task.ProviderTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleTaskService {

    private TaskScheduler scheduler;

    private Map<Integer, ScheduledFuture<?>> tasks = new HashMap<>();

    private ProviderConfigurationProperties config;

    @Autowired
    public ScheduleTaskService(TaskScheduler scheduler, ProviderConfigurationProperties config) {
        this.scheduler = scheduler;
        this.config = config;
    }

    public void addTaskToScheduler(int id, ProviderTask task) {
        ScheduledFuture<?> scheduledTask = scheduler.schedule(task, new PeriodicTrigger(
                config.getFetchDataPeriodicallyEvery(),
                TimeUnit.SECONDS));
        tasks.put(id, scheduledTask);
    }

    public void removeTaskFromScheduler(int id) {
        ScheduledFuture<?> scheduledTask = tasks.get(id);
        if(scheduledTask != null) {
            scheduledTask.cancel(true);
            tasks.remove(id);
        }
    }

    public Set<Integer> getRunningTaskIds() {
        return tasks.keySet();
    }
}
