package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.staticEntities.LightBulbEntity;
import dungeonmania.util.Direction;

public class LightBulbEntityTest {
    @Test
    public void TestLightBulb_Any() {
        String lightBulbLogic = "";
        Map<String, Boolean> activeResults = new HashMap<String, Boolean>();
        activeResults.put("and", true);
        activeResults.put("or", true);
        activeResults.put("xor", true);
        activeResults.put("not", false);
        activeResults.put("co_and", true);
        
        assertTestCasesFor(lightBulbLogic, activeResults);
    }

    @Test
    public void TestLightBulb_And() {
        String lightBulbLogic = "and";
        Map<String, Boolean> activeResults = new HashMap<String, Boolean>();
        activeResults.put("and", true);
        activeResults.put("or", false);
        activeResults.put("xor", false);
        activeResults.put("not", false);
        activeResults.put("co_and", true);
        
        assertTestCasesFor(lightBulbLogic, activeResults);
    }
    
    @Test
    public void TestLightBulb_Or() {
        String lightBulbLogic = "or";
        Map<String, Boolean> activeResults = new HashMap<String, Boolean>();
        activeResults.put("and", true);
        activeResults.put("or", true);
        activeResults.put("xor", true);
        activeResults.put("not", false);
        activeResults.put("co_and", true);
        
        assertTestCasesFor(lightBulbLogic, activeResults);
    }
    
    @Test
    public void TestLightBulb_Xor() {
        String lightBulbLogic = "xor";
        Map<String, Boolean> activeResults = new HashMap<String, Boolean>();
        activeResults.put("and", false);
        activeResults.put("or", false);
        activeResults.put("xor", true);
        activeResults.put("not", false);
        activeResults.put("co_and", false);
        
        assertTestCasesFor(lightBulbLogic, activeResults);
    }
    
    @Test
    public void TestLightBulb_Not() {
        String lightBulbLogic = "not";
        Map<String, Boolean> activeResults = new HashMap<String, Boolean>();
        activeResults.put("and", false);
        activeResults.put("or", false);
        activeResults.put("xor", false);
        activeResults.put("not", true);
        activeResults.put("co_and", false);
        
        assertTestCasesFor(lightBulbLogic, activeResults);
    }
    
    @Test
    public void TestLightBulb_CoAnd() {
        String lightBulbLogic = "co_and";
        Map<String, Boolean> activeResults = new HashMap<String, Boolean>();
        activeResults.put("and", false);
        activeResults.put("or", false);
        activeResults.put("xor", false);
        activeResults.put("not", false);
        activeResults.put("co_and", true);
        
        assertTestCasesFor(lightBulbLogic, activeResults);
    }

    private void assertTestCasesFor(String lightBulbLogic, Map<String, Boolean> activeResults) {
        Assertions.assertAll( "Test case for light bulb logic = " + lightBulbLogic, () -> {
            for (Map.Entry<String, Boolean> entry : activeResults.entrySet()) {
                Dungeon dungeon = getDungeonWithLightBulbTestData(lightBulbLogic);
                LightBulbEntity bulb = (LightBulbEntity) dungeon.entitiesControl.getEntityById("2");
                String testCaseLogic = entry.getKey();
                Boolean isActiveExpected = entry.getValue();
                getCase(testCaseLogic, dungeon);
                assertBulbActivity(isActiveExpected, bulb, lightBulbLogic, testCaseLogic);
            }
        });
    }

    private void assertBulbActivity(Boolean isActiveExpected, LightBulbEntity bulb, String lightBulbLogic, String testCaseLogic) {
        String not = isActiveExpected ? "" : "not ";
        assertEquals(isActiveExpected, bulb.isActive(), lightBulbLogic + " light bulb should " + not + "be active on \"" + testCaseLogic + "\" case");
    }

    private void getCase(String testCaseLogic, Dungeon dungeon) {
        switch (testCaseLogic) {
            case "and":
                // requires all switches to be activated
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(1, 0));
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(0, 1));
                dungeon.tick(Direction.NONE);
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(2, 1));
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(1, 2));
                dungeon.tick(Direction.NONE);
                break;
            case "or":
                // requires 1 or more adjacent activated switches
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(1, 0));
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(0, 1));
                dungeon.tick(Direction.NONE);
                break;
            case "xor":
                // requires 1 and only 1 adjacent activated switch
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(0, 1));
                dungeon.tick(Direction.NONE);
                break;
            case "not":
                // 0 adjacent active switches
                dungeon.tick(Direction.NONE);
                break;
            case "co_and":
                // requires all switches to be activated at once
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(1, 0));
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(0, 1));
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(2, 1));
                dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(1, 2));
                dungeon.tick(Direction.NONE);
                break;
            default:
                break;
        }
    }

    private Dungeon getDungeonWithLightBulbTestData(String logic) {
        /*
           0 1 2
        0  . S .
        1  S B S
        2  . S P
        */
        
        if (!logic.isBlank()) {
            logic = ",\"logic\": \"" + logic + "\"";
        }
        String entities = "{\"entities\": [" +
                "{\"x\": 1,\"y\": 0,\"type\": \"switch\"}," + // S 0

                "{\"x\": 0,\"y\": 1,\"type\": \"switch\"}," + // S 1
                "{\"x\": 1,\"y\": 1,\"type\": \"light_bulb_off\"" + logic + "}," + // B 2
                "{\"x\": 2,\"y\": 1,\"type\": \"switch\"}," + // S 3

                "{\"x\": 1,\"y\": 2,\"type\": \"switch\"}," + // S 4
                "{\"x\": 2,\"y\": 2,\"type\": \"player\"}" + // P 5

                "]}";

        String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
        JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
        JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
        return new Dungeon(entitiesJson, goalsJson, "Standard", "", "");
    }
}
