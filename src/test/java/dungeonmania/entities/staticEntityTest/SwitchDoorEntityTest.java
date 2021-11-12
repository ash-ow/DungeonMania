package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlockerTest;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.Logic;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwitchDoorEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwitchDoorEntityTest implements IEntityTests, IBlockerTest{

    @Override
    public void TestBlock() {
        CharacterEntity player = new CharacterEntity(1, 2);
        SwitchDoorEntity switchDoorEntity = new SwitchDoorEntity(1, 1, Logic.ANY);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(switchDoorEntity);
        player.move(Direction.UP, entitiesControl);
        assertEquals(player.getPosition(), new Position(1, 2));
    }

    @Override
    @Test
    public void TestUnblock() {
        Dungeon dungeon = getDungeonWithSwitchDoorTestData("co_and");
        SwitchDoorEntity switchDoorEntity = (SwitchDoorEntity) dungeon.entitiesControl.getFirstEntityOfType(SwitchDoorEntity.class);
        assertTrue(switchDoorEntity.isBlocking());
        dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(1, 0));
        dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(0, 1));
        dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(2, 1));
        dungeon.entitiesControl.createNewEntityOnMap(new BoulderEntity(1, 2));
        dungeon.tick(Direction.NONE);
        assertFalse(switchDoorEntity.isBlocking());
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        SwitchDoorEntity door = new SwitchDoorEntity(0, 0, Logic.ANY);
        assertEntityResponseInfoEquals(
            door,
            "switch_door-0-0-0",
            EntityTypes.SWITCH_DOOR,
            new Position(0,0,0),
            false
        );
    }

    private Dungeon getDungeonWithSwitchDoorTestData(String logic) {
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
                "{\"x\": 1,\"y\": 1,\"type\": \"switch_door\"" + logic + "}," + // B 2
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
