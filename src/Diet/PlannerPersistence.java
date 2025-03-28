package src.Diet;

public interface PlannerPersistence {
    void save(DayPlannerState state);
    DayPlannerState load();
}