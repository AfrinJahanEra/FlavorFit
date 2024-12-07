package src.FoodRecipes;

import java.util.List;

public class Recipe {
    private String name;
    private String goal;
    private List<String> ingredients;
    private List<String> steps;

    public Recipe(String name, String goal, List<String> ingredients, List<String> steps) {
        this.name = name;
        this.goal = goal;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public String getGoal() {
        return goal;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void display() {
        System.out.println("Recipe Name: " + name);
        System.out.println("Goal: " + goal);
        System.out.println("Ingredients:");
        ingredients.forEach(ingredient -> System.out.println("- " + ingredient));
        System.out.println("Steps:");
        steps.forEach(step -> System.out.println("- " + step));
    }
}
