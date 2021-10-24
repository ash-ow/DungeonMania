package dungeonmania.dungeon.goals;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Goals {
    private ArrayList<IGoal> goals;
    private HashMap<String, Boolean> goalsMap;
    private String reqString;
    private String frontEndString;

    public Goals(JsonObject goalConditions) {
        String goal = goalConditions.get("goal").getAsString();


        if (goal.equals("AND")) {
            JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
            for (JsonElement g : subGoals) {
                JsonObject goalObj = g.getAsJsonObject();
            }
        }
    }

    public String reqStringBuilder(JsonObject goalConditions) {
        String reqString = "";
        String goal = goalConditions.get("goal").getAsString();
        if (goal.equals("AND")) {
            JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
            for (JsonElement g : subGoals) {
                JsonObject goalObj = g.getAsJsonObject();
                reqString += reqStringBuilder(goalObj);
            }
        }
        return reqString;
    }


    public String getGoals() {
        String goalReturn = "";
        for (IGoal goal : goals) {
            goalReturn += goal.getGoal();
        }
        return goalReturn;
    }

    public void completeGoal(IGoal completed) {
        
    }

    public String getReqString() {
        return reqString;
    }

    public String getFrontEndString() {
        return frontEndString;
    }
}
