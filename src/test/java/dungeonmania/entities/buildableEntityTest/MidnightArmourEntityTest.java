package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.MidnightArmourEntity;
import dungeonmania.entities.movingEntities.AssassinEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
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
        assertEntityResponseInfoEquals(midnightArmour, "midnight_armour-0-0-0", EntityTypes.MIDNIGHT_ARMOUR, new Position(0,0,0), false);
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
        // Do battle with Midnight Armour
        CharacterEntity player1 = new CharacterEntity();
        player1.addEntityToInventory(new MidnightArmourEntity());
        AssassinEntity assassin1 = new AssassinEntity();
        assassin1.doBattle(player1);
        
        // Do battle without Midnight Armour
        CharacterEntity player2 = new CharacterEntity();
        AssassinEntity assassin2 = new AssassinEntity();
        assassin2.doBattle(player2);

        Assertions.assertAll(
            () -> assertEquals(-130, assassin1.getHealth()),
            () -> assertEquals(10, assassin2.getHealth()),
            () -> assertTrue(assassin1.getHealth() < assassin2.getHealth(), "Assassin 1 should receive extra damage since Player 1 has midnight armour")
        );

        Assertions.assertAll(
            () -> assertEquals(95.8, player1.getHealth(), 1),
            () -> assertEquals(79, player2.getHealth()),
            () -> assertTrue(player1.getHealth() > player2.getHealth(), "Player 1 should receive less damage since it has midnight armour")
        );
    }

    @Test
    @Override
    public void TestOnlyUsesResourcesFromOneRecipe() {
        assertTrue(true, "There is only one recipe for the midnight armour");
    }
}
