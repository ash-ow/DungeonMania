package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.staticEntities.LightBulbEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WireEntity;
import dungeonmania.util.Position;

public class WireEntityTest implements IEntityTests {

    @Override
    @Test
    public void TestEntityResponseInfo() {
        WireEntity bulb = new WireEntity();
        assertEntityResponseInfoEquals(bulb, "wire-0-0-0", EntityTypes.WIRE, new Position(0,0,0), false);
    }
    
//region followWire
    @Test
    public void TestFollowWire() {
        Dungeon dungeon = getDungeonWithWireTestData();
        LightBulbEntity bulb = (LightBulbEntity) dungeon.entitiesControl.getEntityById("3");
        List<SwitchEntity> adjacentSwitches = bulb.getAdjacentSwitches(dungeon.entitiesControl);
        List<String> actualIds = adjacentSwitches.stream().map(Entity::getId).collect(Collectors.toList());
        List<String> expectedIds = dungeon.entitiesControl.getAllEntitiesOfType(SwitchEntity.class).stream().map(Entity::getId).collect(Collectors.toList());
        assertIterableEqualsAnyOrder(expectedIds, actualIds);
    }
    
    private void assertIterableEqualsAnyOrder(List<String> expectedIds, List<String> actualIds) {
        assertEquals(expectedIds.size(), actualIds.size());
        assertTrue(expectedIds.containsAll(actualIds));
        assertTrue(actualIds.containsAll(expectedIds));
    }

    private Dungeon getDungeonWithWireTestData() {
        /*
           0  1  2  3  4  5
        0  .  S0 .  .  S1 .
        1  S2 B3 W4 W5 W6 .
        2  .  S7 P  .  S8  .
        */
        
        String entities = "{\"entities\": [" +
                "{\"x\": 1,\"y\": 0,\"type\": \"switch\"}," + // S 0
                "{\"x\": 4,\"y\": 0,\"type\": \"switch\"}," + // S 1

                "{\"x\": 0,\"y\": 1,\"type\": \"switch\"}," + // S 2
                "{\"x\": 1,\"y\": 1,\"type\": \"light_bulb_off\"}," + // B 3
                "{\"x\": 2,\"y\": 1,\"type\": \"wire\"}," + // W 4
                "{\"x\": 3,\"y\": 1,\"type\": \"wire\"}," + // W 5
                "{\"x\": 4,\"y\": 1,\"type\": \"wire\"}," + // W 6

                "{\"x\": 1,\"y\": 2,\"type\": \"switch\"}," + // S 7
                "{\"x\": 2,\"y\": 2,\"type\": \"player\"}," + // P
                "{\"x\": 4,\"y\": 2,\"type\": \"switch\"}" + // S 8

                "]}";

        String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
        JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
        JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
        return new Dungeon(entitiesJson, goalsJson, "Standard", "", "");
    }
    
//endregion

//region followWireWithLoop

    @Test
    public void TestFollowWireWithLoop() {
        Dungeon dungeon = getDungeonWithWireTestDataWithLoop();
        LightBulbEntity bulb = (LightBulbEntity) dungeon.entitiesControl.getEntityById("5");
        List<SwitchEntity> adjacentSwitches = bulb.getAdjacentSwitches(dungeon.entitiesControl);
        List<String> actualIds = adjacentSwitches.stream().map(Entity::getId).collect(Collectors.toList());
        List<String> expectedIds = dungeon.entitiesControl.getAllEntitiesOfType(SwitchEntity.class).stream().map(Entity::getId).collect(Collectors.toList());
        assertIterableEqualsAnyOrder(expectedIds, actualIds);
    }

    private Dungeon getDungeonWithWireTestDataWithLoop() {
        /*
           0  1  2  3  4  5 
        0  .  S0 .  W1 W2 W3
        1  S4 B5 W6 W7 S8 W9 
        2  .  S0 P  W1 W2 W3 
        3  .  .  .  .  S4  . 
        */
        
        String entities = "{\"entities\": [" +
                "{\"x\": 1,\"y\": 0,\"type\": \"switch\"}," + // S 0
                "{\"x\": 3,\"y\": 0,\"type\": \"wire\"}," + // W 1
                "{\"x\": 4,\"y\": 0,\"type\": \"wire\"}," + // W 2
                "{\"x\": 5,\"y\": 0,\"type\": \"wire\"}," + // W 3

                "{\"x\": 0,\"y\": 1,\"type\": \"switch\"}," + // S 4
                "{\"x\": 1,\"y\": 1,\"type\": \"light_bulb_off\"}," + // B 5
                "{\"x\": 2,\"y\": 1,\"type\": \"wire\"}," + // W 6
                "{\"x\": 3,\"y\": 1,\"type\": \"wire\"}," + // W 7
                "{\"x\": 4,\"y\": 1,\"type\": \"switch\"}," + // S 8
                "{\"x\": 5,\"y\": 1,\"type\": \"wire\"}," + // W 9

                "{\"x\": 1,\"y\": 2,\"type\": \"switch\"}," + // S 10
                "{\"x\": 2,\"y\": 2,\"type\": \"player\"}," + // P
                "{\"x\": 3,\"y\": 2,\"type\": \"wire\"}," + // W 11
                "{\"x\": 4,\"y\": 2,\"type\": \"wire\"}," + // W 12
                "{\"x\": 5,\"y\": 2,\"type\": \"wire\"}," + // W 13

                "{\"x\": 4,\"y\": 3,\"type\": \"switch\"}" + // S 14

                "]}";

        String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
        JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
        JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
        return new Dungeon(entitiesJson, goalsJson, "Standard", "", "");
    }

//endregion

//region followWireWithNoSwitches


@Test
public void TestFollowWireWithNoSwitches() {
    Dungeon dungeon = getDungeonWithWireTestDataWithNoSwitches();
    LightBulbEntity bulb = (LightBulbEntity) dungeon.entitiesControl.getEntityById("5");
    List<SwitchEntity> adjacentSwitches = bulb.getAdjacentSwitches(dungeon.entitiesControl);
    List<String> actualIds = adjacentSwitches.stream().map(Entity::getId).collect(Collectors.toList());
    List<String> expectedIds = dungeon.entitiesControl.getAllEntitiesOfType(SwitchEntity.class).stream().map(Entity::getId).collect(Collectors.toList());
    assertIterableEqualsAnyOrder(expectedIds, actualIds);
}

private Dungeon getDungeonWithWireTestDataWithNoSwitches() {
    /*
       0  1  2  3  4  5 
    0  .  S0 .  W1 W2 W3
    1  S4 L5 W6 W7 B8 W9 
    2  .  S0 P  W1 W2 W3 
    3  .  .  .  .  B4  . 
    */
    
    String entities = "{\"entities\": [" +
            "{\"x\": 1,\"y\": 0,\"type\": \"switch\"}," + // S 0
            "{\"x\": 3,\"y\": 0,\"type\": \"wire\"}," + // W 1
            "{\"x\": 4,\"y\": 0,\"type\": \"wire\"}," + // W 2
            "{\"x\": 5,\"y\": 0,\"type\": \"wire\"}," + // W 3

            "{\"x\": 0,\"y\": 1,\"type\": \"switch\"}," + // S 4
            "{\"x\": 1,\"y\": 1,\"type\": \"light_bulb_off\"}," + // L 5
            "{\"x\": 2,\"y\": 1,\"type\": \"wire\"}," + // W 6
            "{\"x\": 3,\"y\": 1,\"type\": \"wire\"}," + // W 7
            "{\"x\": 4,\"y\": 1,\"type\": \"boulder\"}," + // S 8
            "{\"x\": 5,\"y\": 1,\"type\": \"wire\"}," + // W 9

            "{\"x\": 1,\"y\": 2,\"type\": \"switch\"}," + // S 10
            "{\"x\": 2,\"y\": 2,\"type\": \"player\"}," + // P
            "{\"x\": 3,\"y\": 2,\"type\": \"wire\"}," + // W 11
            "{\"x\": 4,\"y\": 2,\"type\": \"wire\"}," + // W 12
            "{\"x\": 5,\"y\": 2,\"type\": \"wire\"}," + // W 13

            "{\"x\": 4,\"y\": 3,\"type\": \"boulder\"}" + // B 14

            "]}";

    String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
    JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
    JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
    return new Dungeon(entitiesJson, goalsJson, "Standard", "", "");
}

//endregion

}
