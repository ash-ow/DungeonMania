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

        
        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(EntityTypes.SHIELD.toString(), item.getType());
        }
        assertEquals(1, player.getInventory().size());
    }

    public void assertCanBuildMidnightArmourWithKey() {
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        WoodEntity wood2 = new WoodEntity();
        KeyEntity key = new KeyEntity(0, 0, 1);
        player.addEntityToInventory(wood);
        player.addEntityToInventory(wood2);
        player.addEntityToInventory(key);
        player.build(EntityTypes.SHIELD);
        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(EntityTypes.SHIELD.toString(), item.getType());
        }
        assertTrue(player.getInventory().size() == 1);
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
        inventory.add(new WoodEntity());
        inventory.add(new KeyEntity(0, 0, 1));
        inventory.add(new TreasureEntity());
        assertFalse(midnightArmour.isBuildable(inventory));
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
        assertEntityResponseInfoEquals(midnightArmour, "midnightArmour-0-0-0", EntityTypes.SHIELD, new Position(0,0,0), false);
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
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity player = new CharacterEntity();
        MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
        midnightArmour.contactWithPlayer(new EntitiesControl(), player);

        assertEquals(4,  midnightArmour.getDurability());
        assertEquals(100, player.getHealth());
        assertEquals(50, zombie.getHealth());

        zombie.contactWithPlayer(new EntitiesControl(), player);
        assertEquals(3,  midnightArmour.getDurability(), "MidnightArmour should do two rounds of battle");
        assertFalse(zombie.isAlive());
    }

    @Test
    @Override
    public void TestOnlyUsesResourcesFromOneRecipe() {
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        WoodEntity wood2 = new WoodEntity();
        TreasureEntity treasure = new TreasureEntity();
        KeyEntity key = new KeyEntity(0, 0, 1);
        player.addEntityToInventory(wood);
        player.addEntityToInventory(wood2);
        player.addEntityToInventory(treasure);
        player.addEntityToInventory(key);
        player.build(EntityTypes.SHIELD);
        
        List<EntityTypes> expectedInventory = Arrays.asList(EntityTypes.KEY, EntityTypes.SHIELD);
        List<EntityTypes> actualInventory = player.getInventory().stream().map(item -> item.getType()).collect(Collectors.toList());
        assertIterableEquals(expectedInventory, actualInventory);
    }
}
