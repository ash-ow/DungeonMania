package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.buildableEntities.BowEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;

public class BowTest implements IBuildableEntityTests {
    @Test
    public void TestIsBuildable() {
        BowEntity bow = new BowEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new WoodEntity());
        inventory.add(new ArrowsEntity());
        inventory.add(new ArrowsEntity());
        inventory.add(new ArrowsEntity());
        assertTrue(bow.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_EmptyInventory() {
        BowEntity bow = new BowEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        assertFalse(bow.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InventoryFullOfWrongItems() {
        BowEntity bow = new BowEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new ArrowsEntity());
        inventory.add(new BombEntity());
        assertFalse(bow.isBuildable(inventory));
        
    }

    @Test
    @Override
    public void TestIsNotBuildable_InsufficientCorrectItems() {
        BowEntity bow = new BowEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new WoodEntity());
        inventory.add(new ArrowsEntity());
        inventory.add(new ArrowsEntity());
        assertFalse(bow.isBuildable(inventory));
        
    }

    @Override
    public void TestEntityResponseInfo() {
        BowEntity bow = new BowEntity();
        assertEntityResponseInfoEquals(bow, "bow-0-0-0", "bow", new Position(0,0,0), true);
    }

    @Test
    public void usedBow() {
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        BowEntity bow = new BowEntity();
        bow.setDurability(2);
        player.addEntityToInventory(bow);
        bow.used(player);
        assertEquals(1, bow.getDurability());
        bow.used(player);
        assertNull(player.getInventoryItem(bow.getId()), "Inventory should not contain entity " + bow.getId());
    }
}
