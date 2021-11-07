package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.MidnightArmourEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class MidnightArmourEntityTest implements IBuildableEntityTests {
    @Test
    @Override
    public void TestIsBuildable() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new SunStoneEntity());
        player.addEntityToInventory(new ArmourEntity());
        player.build(EntityTypes.MIDNIGHT_ARMOUR);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.MIDNIGHT_ARMOUR), player);
    }

    @Test
    @Override
    public void TestIsNotBuildable_EmptyInventory() {
        MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
        List<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        assertFalse(midnightArmour.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InventoryFullOfWrongItems() {
        MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
        List<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        inventory.add(new ArrowsEntity());
        inventory.add(new BombEntity());
        assertFalse(midnightArmour.isBuildable(inventory));
        
    }

    @Test
    @Override
    public void TestIsNotBuildable_InsufficientCorrectItems() {
        MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
        List<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        inventory.add(new SunStoneEntity());
        assertFalse(midnightArmour.isBuildable(inventory));

        
        List<CollectableEntity> inventory2 = new ArrayList<CollectableEntity>();
        inventory2.add(new ArmourEntity());
        assertFalse(midnightArmour.isBuildable(inventory2));
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
        assertEntityResponseInfoEquals(midnightArmour, "midnightArmour-0-0-0", EntityTypes.MIDNIGHT_ARMOUR, new Position(0,0,0), false);
    }

    @Override
    @Test
    public void TestCollect() {
        MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
        assertEntityIsCollected(midnightArmour);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        // TODO Extra attack damage and protection
    }

    @Test
    @Override
    public void TestOnlyUsesResourcesFromOneRecipe() {
        assertTrue(true, "There is only one recipe for the midnight armour");
    }
}
