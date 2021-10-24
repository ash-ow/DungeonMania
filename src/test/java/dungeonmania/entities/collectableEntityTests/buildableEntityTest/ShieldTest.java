package dungeonmania.entities.collectableEntityTests.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.ShieldEntity;

public class ShieldTest implements IBuildableEntityTests {
    @Test
    public void TestIsBuildable() {
        ShieldEntity shield = new ShieldEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new Wood());
        inventory.add(new Arrows());
        inventory.add(new Arrows());
        inventory.add(new Arrows());
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
        inventory.add(new Arrows());
        inventory.add(new Bomb());
        assertFalse(shield.isBuildable(inventory));
        
    }

    @Test
    @Override
    public void TestIsNotBuildable_InsufficientCorrectItems() {
        ShieldEntity shield = new ShieldEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new Wood());
        inventory.add(new Arrows());
        inventory.add(new Arrows());
        assertFalse(shield.isBuildable(inventory));
        
    }
}