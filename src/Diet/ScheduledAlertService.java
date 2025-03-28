package src.Diet;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public class ScheduledAlertService implements AlarmScheduler, AlarmMonitor, AlarmLifecycle {
    private final ScheduledExecutorService scheduler;
    private final List<String> triggeredAlarms;
    private final NotificationService notificationService;

    public ScheduledAlertService(NotificationService notificationService) {
        this.scheduler = Executors.newScheduledThreadPool(4);
        this.triggeredAlarms = new ArrayList<>();
        this.notificationService = notificationService;
    }

    @Override
    public void scheduleAlarm(LocalDateTime triggerTime, String message) {
        Duration delay = Duration.between(LocalDateTime.now(), triggerTime);
        if (delay.isNegative()) delay = delay.plusDays(1);

        scheduler.schedule(() -> triggerAlarm(message), delay.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void scheduleRelativeAlarm(long minutes, String message) {
        scheduler.schedule(() -> triggerAlarm(message), minutes, TimeUnit.MINUTES);
    }

    private void triggerAlarm(String message) {
        triggeredAlarms.add(message);
        notificationService.showVisualAlert(message);
        notificationService.playSound();
    }

    @Override
    public List<String> getTriggeredAlarms() {
        return Collections.unmodifiableList(triggeredAlarms);
    }

    @Override
    public void shutdown() {
        scheduler.shutdown();
    }
}