package dungeonmania.dungeon.goals;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Goals {
    private ArrayList<IGoal> goals;

    public Goals(JsonObject goalConditions) {
        String goal = goalConditions.get("goal").getAsString();
        if (goal.equals("AND") || goal.equals("OR")) {
            JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
            for (JsonElement g : subGoals) {
                
            }
        }
    }

    public String getGoals() {
        String goalReturn = "";
        
    }

}
