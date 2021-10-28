package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

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
        // Create a dungeon
        // pass a key into the constructor
        // assert the keys and doors have corresponding qualities
        // e.g. key.number == 1 and unlocks "door-0-0-0"
    }
}
