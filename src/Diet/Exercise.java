package src.Diet;

import java.time.Duration;
import java.time.LocalDateTime;

public class Exercise {
    private final String name;
    private final Duration duration;
    private final LocalDateTime performedAt;

    public Exercise(String name, Duration duration) {
        this.name = name;
        this.duration = duration;
        this.performedAt = LocalDateTime.now();
    }

    public String getName() { return name; }
    public Duration getDuration() { return duration; }
    public LocalDateTime getPerformedAt() { return performedAt; }
}