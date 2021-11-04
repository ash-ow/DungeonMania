package dungeonmania.dungeon.goals;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GoalsTest {
    @Test
    public void frontEndStringMultiple() {
        String jsonGoals = "{ \"goal\": \"AND\", \"subgoals\": [{\"goal\": \"enemies\"}, {\"goal\": \"treasure\"}]}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals(":mercenary AND :treasure", g.getFrontEndString());
    }

    @Test
    public void frontEndStringSingle() {
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals(":exit", g.getFrontEndString());
    }

    @Test
    public void frontEndStringComplex() {
        String jsonGoals = "{ \"goal\": \"AND\", \"subgoals\": [{\"goal\": \"enemies\"}, {\"goal\": \"OR\", \"subgoals\": [{\"goal\": \"exit\"}, {\"goal\": \"treasure\"}]}]}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals(":mercenary AND :exit/:treasure" , g.getFrontEndString());
    }
}