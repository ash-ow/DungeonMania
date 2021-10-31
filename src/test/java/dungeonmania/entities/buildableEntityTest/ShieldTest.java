package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.buildableEntities.ShieldEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.util.Position;

public class ShieldTest implements IBuildableEntityTests {
    @Test
    public void TestIsBuildable() {
        // ShieldEntity shield = new ShieldEntity();
        // List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        // inventory.add(new WoodEntity());
        // inventory.add(new ArrowsEntity());
        // inventory.add(new ArrowsEntity());
        // inventory.add(new ArrowsEntity());
        // assertTrue(shield.isBuildable(inventory));
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
        inventory.add(new ArrowsEntity());
        inventory.add(new ArrowsEntity());
        assertFalse(shield.isBuildable(inventory));
        
    }

    @Override
    public void TestEntityResponseInfo() {
        ShieldEntity shield = new ShieldEntity();
        assertEntityResponseInfoEquals(shield, "shield-0-0-0", EntityTypes.SHIELD, new Position(0,0,0), false);
    }
}
