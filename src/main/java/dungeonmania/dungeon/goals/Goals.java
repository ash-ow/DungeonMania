package dungeonmania.dungeon.goals;

import java.util.ArrayList;
import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;

public class Goals {
    private ArrayList<IGoal> goals;
    private HashMap<String, Boolean> goalsMap;
    private String reqString;
    private String frontEndString;

    public Goals(JsonObject goalConditions) {
        this.goalsMap = new HashMap<>();
        this.goals = new ArrayList<>();
        this.reqString = reqStringBuilder(goalConditions);     
        this.frontEndString = frontEndStringBuilder(goalConditions);  
    }

    public String reqStringBuilder(JsonObject goalConditions) {
        String reqString = "";
        String goal = goalConditions.get("goal").getAsString();
        if (goal.equals("AND")) {
            JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
            reqString += "(";
            for (int i = 0; i < subGoals.size() - 1; i++) {
                JsonObject goalObj = subGoals.get(i).getAsJsonObject();
                reqString += reqStringBuilder(goalObj) + " && ";
            }
            JsonObject goalObj = subGoals.get(subGoals.size()-1).getAsJsonObject();
            reqString += reqStringBuilder(goalObj) + ")";
        }
        else if (goal.equals("OR")) {
            JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
            reqString += "(";
            for (int i = 0; i < subGoals.size() - 1; i++) {
                JsonObject goalObj = subGoals.get(i).getAsJsonObject();
                reqString += reqStringBuilder(goalObj) + " || ";
            }
            JsonObject goalObj = subGoals.get(subGoals.size()-1).getAsJsonObject();
            reqString += reqStringBuilder(goalObj) + ")";
        } else {
            reqString = goal;
            goalsMap.put(goal, false);
        }
        return reqString;
    }

    public String frontEndStringBuilder(JsonObject goalConditions) {
        String reqString = "";
        String goal = goalConditions.get("goal").getAsString();
        if (goal.equals("AND")) {
            JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
            for (int i = 0; i < subGoals.size() - 1; i++) {
                JsonObject goalObj = subGoals.get(i).getAsJsonObject();
                reqString += frontEndStringBuilder(goalObj) + " AND ";
            }
            JsonObject goalObj = subGoals.get(subGoals.size()-1).getAsJsonObject();
            reqString += frontEndStringBuilder(goalObj);
        }
        else if (goal.equals("OR")) {
            JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
            for (int i = 0; i < subGoals.size() - 1; i++) {
                JsonObject goalObj = subGoals.get(i).getAsJsonObject();
                reqString += frontEndStringBuilder(goalObj) + "/";
            }
            JsonObject goalObj = subGoals.get(subGoals.size()-1).getAsJsonObject();
            reqString += frontEndStringBuilder(goalObj);
        } else {
            reqString = ":" + goal;
            goalsMap.put(goal, false);
            addGoal(goal);
        }
        return reqString;
    }

    public void addGoal(String goal) {
        switch (goal) {
            case "exit":
                goals.add(new ExitGoal("exit"));
                break;
            case "enemies":
                goals.add(new DestroyGoal());
                break;
            case "boulders":
                goals.add(new BoulderGoal());
                break;
            case "treasure":
                goals.add(new CollectingGoal());
                break;
        }
    }

    public String getReqString() {
        return reqString;
    }

    public String checkGoals(Dungeon dungeon) {
        System.out.println(reqString);
        for (IGoal goal : goals) {
            goal.checkGoal(dungeon, goalsMap);
        }
        String boolParse = reqString;
        for (String key : goalsMap.keySet()) {
            boolParse = boolParse.replace(key, goalsMap.get(key).toString());
        }
        System.out.println(boolParse);
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");
        boolean check = false;
        try {
            check = (Boolean) se.eval(boolParse);
        } catch (ScriptException e) {
            System.out.println("Invalid Expression");
        }
        System.out.println(check);
        if (check) {
            return "";
        } else {
            return frontEndString;
        }
    }

    public String getFrontEndString() {
        return frontEndString;
    }

    public static void main(String[] args) {
        String boolParse = "true && true";
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");
        try {
            System.out.println(se.eval(boolParse));

        } catch (ScriptException e) {

            System.out.println("Invalid Expression");

        }
    }
}
