package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.google.gson.Gson;
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

public class DoorEntityTest implements IEntityTests {
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
        ArrayList<IEntity> entities = new ArrayList<>();

        CharacterEntity player = new CharacterEntity();
        entities.add(player);
        KeyEntity key = new KeyEntity(0, 1, 0, 1);
        entities.add(key);
        DoorEntity doorCorrect = new DoorEntity(0, 2, 0, 1);
        entities.add(doorCorrect);
        DoorEntity doorIncorrect = new DoorEntity(0, 3, 0, 2);
        entities.add(doorIncorrect);
        
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertNotNull(player.getInventory().getEntityById("key-0-1-0"), "Inventory should contain the key");

        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 2, 0), player.getPosition(), "Player should be able to move into the first door as they have key #1");
        
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 2, 0), player.getPosition(), "Player should NOT be able to move into the second door as they lack key #1");
    }

    @Test
    public void TestCreateDoor() {
        String dungeonJson = "{\"entities\": [{\"x\": 0,\"y\": 0,\"type\": \"door\",\"key\": 1},{\"x\": 0,\"y\": 1,\"type\": \"key\",\"key\": 1},{\"x\": 0,\"y\": 2,\"type\": \"key\",\"key\": 2}]}";
        JsonObject jsonObject = new Gson().fromJson(dungeonJson, JsonObject.class);
        Dungeon dungeon = new Dungeon(20, 20, jsonObject.get("entities").getAsJsonArray(), new JsonObject(), "", "", "");

        DoorEntity door = (DoorEntity) dungeon.entitiesControl.getEntityById("door-0-0-0");
        KeyEntity key = (KeyEntity) dungeon.entitiesControl.getEntityById("key-0-1-0");
        KeyEntity key2 = (KeyEntity) dungeon.entitiesControl.getEntityById("key-0-2-0");

        Assertions.assertAll(
                () -> assertNotNull(door, "Door should exist"),
                () -> assertNotNull(key, "Key should exist"),
                () -> assertNotNull(key2, "Key2 should exist"),
                () -> assertEquals(1, door.getKeyNumber()),
                () -> assertEquals(1, key.getKeyNumber()),
                () -> assertEquals(2, key2.getKeyNumber()),
                () -> assertTrue(key.unlocks(door)),
                () -> assertFalse(key2.unlocks(door))
            );

    }
}
