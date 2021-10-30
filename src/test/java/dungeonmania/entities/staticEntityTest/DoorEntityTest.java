package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.DoorEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DoorEntityTest implements IEntityTests, IBlockerTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        DoorEntity door = new DoorEntity(0);
        assertEntityResponseInfoEquals(
            door,
            "door-0-0-0",
            "door",
            new Position(0,0,0),
            false
        );
    }

    @Test
    public void TestUnlockDoor() {
        Dungeon dungeon = getDungeonWithDoorTestData();
        CharacterEntity player = (CharacterEntity)dungeon.getPlayer();
        assertNotNull(player, "Dungeon should contain the player");

        dungeon.tick(Direction.DOWN);
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 2, 0), player.getPosition(), "Player should move into key's position");
        assertNotNull(player.getInventory().getEntityById("key-0-1-0"), "Inventory should contain key 1");
        assertNotNull(player.getInventory().getEntityById("key-0-2-0"), "Inventory should contain key 2");

        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 3, 0), player.getPosition(), "Player should be able to move into the first door as they have key #1");
        assertNotNull(player.getInventory().getEntityById("key-0-1-0"), "Unlocking door should not consume key 3");
        assertNull(player.getInventory().getEntityById("key-0-2-0"), "Unlocking door should consume key 1");
        
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 3, 0), player.getPosition(), "Player should NOT be able to move into the second door as they lack key #1");
    }

    @Test
    public void TestCreateDoor() {
        Dungeon dungeon = getDungeonWithDoorTestData();

        DoorEntity door = (DoorEntity) dungeon.entitiesControl.getEntityById("door-0-3-0");
        KeyEntity key_correct = (KeyEntity) dungeon.entitiesControl.getEntityById("key-0-2-0");
        KeyEntity key_dummy = (KeyEntity) dungeon.entitiesControl.getEntityById("key-0-1-0");

        Assertions.assertAll(
                () -> assertNotNull(door, "Door should exist"),
                () -> assertNotNull(key_correct, "Key should exist"),
                () -> assertNotNull(key_dummy, "Key should exist"),

                () -> assertEquals(1, door.getKeyNumber()),
                () -> assertEquals(1, key_correct.getKeyNumber()),
                () -> assertEquals(3, key_dummy.getKeyNumber()),

                () -> assertEquals(door.getKeyNumber(), key_correct.getKeyNumber()),
                () -> assertNotEquals(door.getKeyNumber(), key_dummy.getKeyNumber())
            );

    }

    private Dungeon getDungeonWithDoorTestData() {
        /*
        Map:

            0
        0   P
        1   K3 - dummy key
        2   K1 - unlocks next door
        3   D1 - can unlock
        4   D2 - cannot unlock
        5   K2 - cannot  reach K2
        6   Exit
        
        */
        String entities = "{\"entities\": [" +
            "{\"x\": 0,\"y\": 0,\"type\": \"player\"}," +
            "{\"x\": 0,\"y\": 1,\"type\": \"key\",\"key\": 3}," +
            "{\"x\": 0,\"y\": 2,\"type\": \"key\",\"key\": 1}," +
            "{\"x\": 0,\"y\": 3,\"type\": \"door\",\"key\": 1}," +
            "{\"x\": 0,\"y\": 4,\"type\": \"door\",\"key\": 2}," +
            "{\"x\": 0,\"y\": 5,\"type\": \"key\",\"key\": 3}," +
            "{\"x\": 0,\"y\": 6,\"type\": \"exit\"}" +
            "]}";
        String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
        JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
        JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
        return new Dungeon(entitiesJson, goalsJson, "", "", "");
    }
}
