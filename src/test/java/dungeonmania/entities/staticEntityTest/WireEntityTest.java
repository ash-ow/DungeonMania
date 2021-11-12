package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.cert.CollectionCertStoreParameters;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
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
    
    @Test
    public void TestFollowWire() {
        Dungeon dungeon = getDungeonWithWireTestData();
        LightBulbEntity bulb = (LightBulbEntity) dungeon.entitiesControl.getEntityById("3");
        List<SwitchEntity> adjacentSwitches = bulb.getAdjacentSwitches(dungeon.entitiesControl);
        List<String> actualIds = adjacentSwitches.stream().map(Entity::getId).collect(Collectors.toList());
        List<String> expectedIds = dungeon.entitiesControl.getAllEntitiesOfType(SwitchEntity.class).stream().map(Entity::getId).collect(Collectors.toList());

        System.out.println(actualIds);
        System.out.println(expectedIds);
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
}
