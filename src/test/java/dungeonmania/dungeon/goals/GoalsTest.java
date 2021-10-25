package dungeonmania.dungeon.goals;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class GoalsTest {
    @Test
    public void testReqStringMultiple() {
        String jsonGoals = "{ \"goal\": \"AND\", \"subgoals\": [{\"goal\": \"enemies\"}, {\"goal\": \"treasure\"}]}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals("(enemies && treasure)", g.getReqString());
    }

    @Test
    public void testReqStringSingle() {
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals("exit", g.getReqString());
    }

    @Test
    public void testReqStringComplex() {
        String jsonGoals = "{ \"goal\": \"AND\", \"subgoals\": [{\"goal\": \"enemies\"}, {\"goal\": \"OR\", \"subgoals\": [{\"goal\": \"exit\"}, {\"goal\": \"treasure\"}]}]}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals("(enemies && (exit || treasure))" , g.getReqString());
    }

    @Test
    public void frontEndStringMultiple() {
        String jsonGoals = "{ \"goal\": \"AND\", \"subgoals\": [{\"goal\": \"enemies\"}, {\"goal\": \"treasure\"}]}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals(":enemies AND :treasure", g.getFrontEndString());
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
        assertEquals(":enemies AND :exit/:treasure" , g.getFrontEndString());
    }
}