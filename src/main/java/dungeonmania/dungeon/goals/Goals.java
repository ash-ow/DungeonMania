package dungeonmania.dungeon.goals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;

import java.util.function.*;

import java.util.regex.*;

public class Goals {
    private ArrayList<IGoal> goals;
    private HashMap<String, Boolean> goalsMap;
    private String reqString;
    private String frontEndString;
    static String REQTYPE = "req";
    static String FRONTTYPE = "front";

    public Goals(JsonObject goalConditions) {
        this.goalsMap = new HashMap<>();
        this.goals = new ArrayList<>();
        if (goalConditions == null) {
            this.reqString = "";
            this.frontEndString = "";
        } else {
            this.reqString = reqStringBuilder(goalConditions);     
            this.frontEndString = frontEndStringBuilder(goalConditions);  
        }
    }


    public String reqStringBuilder(JsonObject goalConditions) {
        String goal = goalConditions.get("goal").getAsString();
        if (goal.equals("AND")) {
            return "(" + readSubGoals(" && ", goalConditions, REQTYPE) + ")";
        }
        else if (goal.equals("OR")) {
            return "(" + readSubGoals(" || ", goalConditions, REQTYPE) + ")";
        } else {
            goalsMap.put(goal, false);
            addGoal(goal);
            return goal;
        }
    }

    public String frontEndStringBuilder(JsonObject goalConditions) {
        String goal = goalConditions.get("goal").getAsString();
        if (goal.equals("AND")) {
            return readSubGoals(" AND ", goalConditions, FRONTTYPE);
        }
        else if (goal.equals("OR")) {
            return readSubGoals("/", goalConditions, FRONTTYPE);
        } else {
            return ":" + goal;           
        }
    }

    public String readSubGoals(String input, JsonObject goalConditions, String type) {
        JsonArray subGoals = goalConditions.getAsJsonArray("subgoals");
        String reqString = "";
        for (int i = 0; i < subGoals.size() - 1; i++) {
            JsonObject goalObj = subGoals.get(i).getAsJsonObject();
            if (type.equals("req")) {
                reqString += reqStringBuilder(goalObj) + input;
            } else {
                reqString += frontEndStringBuilder(goalObj) + input;
            }           
        }
        JsonObject goalObj = subGoals.get(subGoals.size()-1).getAsJsonObject();
        if (type.equals("req")) {
            reqString += reqStringBuilder(goalObj);
        } else {
            reqString += frontEndStringBuilder(goalObj);
        } 
        return reqString;
    }

    public void addGoal(String goal) {
        switch (goal) {
            case "exit":
                goals.add(new ExitGoal());
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

    @SuppressWarnings("all")
    public String checkGoals(Dungeon dungeon) {
        for (IGoal goal : goals) {
            goalsMap.put(goal.getType(), goal.checkGoal(dungeon));
        }
        String boolParse = reqString;
        for (String key : goalsMap.keySet()) {
            boolParse = boolParse.replace(key, goalsMap.get(key).toString());
        }
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");

        boolean check = false;
        if (!boolParse.isEmpty()) {
            try {
                check = (Boolean) se.eval(boolParse);
            } catch (ScriptException e) {
                System.out.println("Invalid Expression");
            }
        }
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
        String str = "(true && false && (true || (false && true)))";
        for (String s : str.split("[\\(\\)]")) {
            System.out.println(s);
        }



        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");
        try {
            System.out.println((Boolean) se.eval("true"));
        } catch (ScriptException e) {
            System.out.println("Invalid Expression");
        }
    }
}
