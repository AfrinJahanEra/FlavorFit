package src.Interface;
import src.DietPlanner.*;

public interface TargetStorage {
    UserTarget loadTargets();

    void saveTargets(UserTarget userTarget);
}