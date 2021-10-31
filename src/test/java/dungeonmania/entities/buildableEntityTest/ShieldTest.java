package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.buildableEntities.ShieldEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;

public class ShieldTest implements IBuildableEntityTests {
    @Test
    public void TestIsBuildable() {
        ShieldEntity shield = new ShieldEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new WoodEntity());
        inventory.add(new WoodEntity());
        inventory.add(new TreasureEntity());
        assertTrue(shield.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_EmptyInventory() {
        ShieldEntity shield = new ShieldEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        assertFalse(shield.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InventoryFullOfWrongItems() {
        ShieldEntity shield = new ShieldEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new ArrowsEntity());
        inventory.add(new BombEntity());
        assertFalse(shield.isBuildable(inventory));
        
    }

    @Test
    @Override
    public void TestIsNotBuildable_InsufficientCorrectItems() {
        ShieldEntity shield = new ShieldEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new WoodEntity());
        inventory.add(new KeyEntity(0,0,0,1));
        inventory.add(new TreasureEntity());
        assertFalse(shield.isBuildable(inventory));
    }

    @Override
    public void TestEntityResponseInfo() {
        ShieldEntity shield = new ShieldEntity();
        assertEntityResponseInfoEquals(shield, "shield-0-0-0", EntityTypes.SHIELD, new Position(0,0,0), false);
    }

    @Test
    public void usedShield() {
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        ShieldEntity shield = new ShieldEntity();
        shield.setDurability(2);
        player.addEntityToInventory(shield);
        shield.used(player);
        assertEquals(1, shield.getDurability());
        shield.used(player);
        assertNull(player.getInventoryItem(shield.getId()), "Inventory should not contain entity " + shield.getId());
    }
}
