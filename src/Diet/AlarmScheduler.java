package src.Diet;

import java.time.LocalDateTime;

public interface AlarmScheduler {
    void scheduleAlarm(LocalDateTime triggerTime, String message);
    void scheduleRelativeAlarm(long minutes, String message);
}