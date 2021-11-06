package dungeonmania.dungeon.goals;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;

public class Goals {
    private IGoal goal;

    /**
     * Goal constructor
     * @param goalConditions    conditions for the goal
     */
    public Goals(JsonObject goalConditions) {
        this.goal = buildGoal(goalConditions);
    }

    /**
     * Builds the goal
     * @param goalConditions    conditions for the goal
     * @return the list of subgoals
     */
    private IGoal buildGoal(JsonObject goalConditions) {
        String goal = goalConditions.get("goal").getAsString();
        JsonArray jsonSubGoals = goalConditions.getAsJsonArray("subgoals");
        List<IGoal> subGoals = new ArrayList<>();
        switch (goal) {
            case "exit":
                return new ExitGoal();
            case "enemies":
                return new DestroyGoal();
            case "boulders":
                return new BoulderGoal();
            case "treasure":
                return new CollectingGoal();
            case "AND":               
                for (JsonElement goalElement : jsonSubGoals) {
                    IGoal addedGoal = buildGoal(goalElement.getAsJsonObject());
                    subGoals.add(addedGoal);
                }
                return new ANDGoal(subGoals);
            case "OR":
                for (JsonElement goalElement : jsonSubGoals) {
                    IGoal addedGoal = buildGoal(goalElement.getAsJsonObject());
                    subGoals.add(addedGoal);
                }
                return new ORGoal(subGoals);
            default:
                return null;
        }
    }

    /**
     * Evaluates whether goal has passed
     * @param dungeon  dungeon to be checked
     */
    public String checkGoals(Dungeon dungeon) {
        if (goal.checkGoal(dungeon)) {
            return "";
        } else {
            return goal.getFrontendString();
        }
    }

    public String getFrontEndString() {
        return goal.getFrontendString();
    }
}
