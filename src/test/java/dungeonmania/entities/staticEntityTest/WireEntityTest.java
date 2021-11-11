package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

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
        assertEntityResponseInfoEquals(bulb, "light_bulb_off-0-0-0", EntityTypes.WIRE, new Position(0,0,0), false);
    }
    
    @Override
    @Test
    public void TestFollowWire() {
        Dungeon dungeon = getDungeonWithWireTestData();
        LightBulbEntity bulb = (LightBulbEntity) dungeon.entitiesControl.getEntityById("3");
        List<SwitchEntity> adjacentSwitches = bulb.getAdjacentSwitches(dungeon.entitiesControl);
        List<String> actualIds = adjacentSwitches.stream().map(Entity::getId).collect(Collectors.toList());
        List<String> expectedIds = Arrays.asList("0","1","2","8","10");
        assertIterableEquals(expectedIds, actualIds);
    }
    
    private Dungeon getDungeonWithWireTestData() {
        /*
           0 1 2 3 4 5
        0  . S . . S .
        1  S B W W W .
        2  . S P . S .
        */
        
        String entities = "{\"entities\": [" +
                "{\"x\": 1,\"y\": 0,\"type\": \"switch\"}," + // S 0
                "{\"x\": 4,\"y\": 0,\"type\": \"switch\"}," + // S 1

                "{\"x\": 0,\"y\": 1,\"type\": \"switch\"}," + // S 2
                "{\"x\": 1,\"y\": 1,\"type\": \"light_bulb_off\"}," + // B 3
                "{\"x\": 2,\"y\": 1,\"type\": \"wire\"}," + // W 4
                "{\"x\": 3,\"y\": 1,\"type\": \"wire\"}," + // W 5
                "{\"x\": 4,\"y\": 1,\"type\": \"wire\"}," + // W 6
                "{\"x\": 4,\"y\": 1,\"type\": \"wire\"}," + // W 7

                "{\"x\": 1,\"y\": 2,\"type\": \"switch\"}," + // S 8
                "{\"x\": 2,\"y\": 2,\"type\": \"player\"}" + // P 9
                "{\"x\": 4,\"y\": 2,\"type\": \"switch\"}," + // S 10

                "]}";

        String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
        JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
        JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
        return new Dungeon(entitiesJson, goalsJson, "Standard", "", "");
    }
}
