package src.DietPlanner;

interface TargetStorage {
    UserTarget loadTargets();

    void saveTargets(UserTarget userTarget);
}