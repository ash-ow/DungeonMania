package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.util.Position;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.util.Direction;
import dungeonmania.dungeon.Dungeon;


public class KeyEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        KeyEntity key = new KeyEntity(0,0,1);
        assertEntityResponseInfoEquals(
            key,
            "key-0-0-0",
            EntityTypes.KEY,
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestCollect() {
        KeyEntity key = new KeyEntity(0, 0, 0);
        assertEntityIsCollected(key);
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }

    @Test 
    //This test references M2 Test4-2 "Test picking up a key puts the key in the inventory"
    public void TestKeyInInventory() {
        CharacterEntity player = new CharacterEntity(0, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        KeyEntity key = new KeyEntity(0, 1, 0);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        entities.add(key);
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 1, 0), key.getPosition());
        assertItemInInventory("key-0-1-0", player, dungeon.entitiesControl);
        assertTrue(key.keyPickedUp(), "Key can be picked up");
    }
/*
    @Test 
    //This test references M2 Test13-5 "Test player cannot pickup two keys"
    public void TestCollectOnlyOneKey(){
        CharacterEntity player = new CharacterEntity(0, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        KeyEntity key1 = new KeyEntity(0, 1, 1);
        KeyEntity key2 = new KeyEntity(0, 2, 2);
        entities.add(key1);
        entities.add(key2);
        // Check that the Player has no keys initially.
        assertTrue(!player.hasKey());
        // Make Player pick up the first Key. Check that the Player now has a key.
        player.move(Direction.DOWN);
        assertTrue(player.hasKey());
        assertEquals(player.getInventory().size(), 1);
        assertNotNull(player.getInventoryItem("0"), "Key should be in inventory");
        //Player should not be able to move down and pick up a second key i.e player cannot move into second key
        player.move(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 2, 0), key2.getPosition());
    }
    */
}