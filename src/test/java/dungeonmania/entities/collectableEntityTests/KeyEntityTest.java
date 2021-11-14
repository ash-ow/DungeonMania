package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.Position;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.util.Direction;
import dungeonmania.dungeon.Dungeon;
import dungeonmania.response.models.ItemResponse;


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
    @Test
    public void TestUseCollectable() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0);
        KeyEntity key = new KeyEntity(0, 0, 0); 
        entities.add(key);
        key.used(player);
        assertFalse(key.keyPickedUp(), "No Key has been picked up");
        assertNull(player.getInventory().getInventoryItemById(key.getId()), "Inventory should not contain entity " + key.getId());
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
        assertNotNull(player.getInventory().getInventoryItemById(key.getId()), "Inventory should contain entity " + key.getId());
    }

    @Test 
    public void TestKeyPickedUp() {
        KeyEntity key = new KeyEntity(0, 0, 0);
        CharacterEntity player = new CharacterEntity(0, 0);
        player.getInventoryItems().add(key);
        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(EntityTypes.KEY.toString(), item.getType());
        }
        assertTrue(key.keyPickedUp(), "A key has been picked up");
    }

    @Test 
    //This test references M2 Test13-5 "Test player cannot pickup two keys"
    public void TestCollectOnlyOneKey(){
        CharacterEntity player = new CharacterEntity(0, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        KeyEntity key1 = new KeyEntity(0, 1, 1);
        KeyEntity key2 = new KeyEntity(0, 2, 2);
        Dungeon d1 = new Dungeon(entities, "Standard", player);
        entities.add(key1);
        entities.add(key2);
        // Check that the Player has no keys initially.
        assertFalse(key1.keyPickedUp(), "No key should be in inventory");
        // Make Player pick up the first Key. Check that the Player now has a key.
        d1.tick(Direction.DOWN);
        assertTrue(key1.keyPickedUp(), "Key 1 should be in inventory");
        assertNotNull(player.getInventory().getInventoryItemById(key1.getId()), "Inventory should contain entity " + key1.getId());
        //Player should not be able to pick up a second key
        d1.tick(Direction.DOWN);
        assertFalse(key2.keyPickedUp(), "Key 2 should not be in inventory");
        assertNull(player.getInventory().getInventoryItemById(key2.getId()), "Inventory should not contain entity " + key2.getId());
    }
    
    @Test 
    public void TestSecondKeyAfterFirstKeyUse(){
        CharacterEntity player = new CharacterEntity(0, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        KeyEntity key1 = new KeyEntity(0, 1, 1);
        KeyEntity key2 = new KeyEntity(0, 2, 2);
        Dungeon d1 = new Dungeon(entities, "Standard", player);
        entities.add(key1);
        entities.add(key2);
        // Make Player pick up the first Key. Check that the Player now has a key.
        d1.tick(Direction.DOWN);
        assertTrue(key1.keyPickedUp(), "Key 1 should be in inventory");
        assertNotNull(player.getInventory().getInventoryItemById(key1.getId()), "Inventory should contain entity " + key1.getId());
        //Player uses first key 
        key1.used(player);
        assertFalse(key1.keyPickedUp(), "No Key has been picked up");
        assertNull(player.getInventory().getInventoryItemById(key1.getId()), "Inventory should not contain entity " + key1.getId());
        //Player can move to second key 
        d1.tick(Direction.DOWN);
        assertEquals(new Position(0, 2, 0), player.getPosition());
        assertEquals(new Position(0, 2, 0), key2.getPosition());
        assertTrue(key2.keyPickedUp(), "Key 2 should be in inventory");
        assertNotNull(player.getInventory().getInventoryItemById(key2.getId()), "Inventory should contain entity " + key2.getId());
    }   
}