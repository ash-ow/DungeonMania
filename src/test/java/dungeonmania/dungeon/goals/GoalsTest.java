package dungeonmania.dungeon.goals;

import com.google.gson.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class GoalsTest {
    @Test
    public void testGettingGoals() {
        String jsonGoals = "{\"goal-condition\": { \"goal\": \"AND\", \"subgoals\": [{\"goal\": \"enemies\"}, {\"goal\": \"treasure\"}]}}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals("enemies treasure", g.getGoals());
    }

    @Test
    public void testSingleGoal() {
        String jsonGoals = "{\"goal-condition\": { \"goal\": \"exit\"}}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals("exit", g.getGoals());
    }

    @Test
    public void testReqString() {
        String jsonGoals = "{\"goal-condition\": { \"goal\": \"AND\", \"subgoals\": [{\"goal\": \"enemies\"}, {\"goal\": \"OR\", \"subgoals\": [{\"goal\": \"exit\"}, {\"goal\": \"treasure\"}]}]}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Goals g = new Goals(j);
        assertEquals("(enemies && (exit || treasure))" , g.getGoals());
    }

}
