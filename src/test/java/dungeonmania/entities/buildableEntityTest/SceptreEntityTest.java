package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.SceptreEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;

public class SceptreEntityTest implements IBuildableEntityTests {

// region IsBuildable
    @Test
    @Override
    public void TestIsBuildable() {
        Assertions.assertAll( "Can build sceptre with one wood or two arrows, one key/treasure, and one sun stone",
            () -> assertCanBuildSceptreWithWoodKeySunstone(),
            () -> assertCanBuildSceptreWithWoodTreasureSunStone(),
            () -> assertCanBuildSceptreWith2ArrowsTreasureSunStone(),
            () -> assertCanBuildSceptreWith2ArrowsKeySunStone()   
        );
    }

    public void assertCanBuildSceptreWithWoodKeySunstone() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new WoodEntity());
        player.addEntityToInventory(new KeyEntity(1));
        player.addEntityToInventory(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }

    public void assertCanBuildSceptreWithWoodTreasureSunStone() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new WoodEntity());
        player.addEntityToInventory(new TreasureEntity());
        player.addEntityToInventory(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }

    public void assertCanBuildSceptreWith2ArrowsTreasureSunStone() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new ArrowsEntity());
        player.addEntityToInventory(new ArrowsEntity());
        player.addEntityToInventory(new TreasureEntity());
        player.addEntityToInventory(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }

    public void assertCanBuildSceptreWith2ArrowsKeySunStone() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new ArrowsEntity());
        player.addEntityToInventory(new ArrowsEntity());
        player.addEntityToInventory(new KeyEntity(1));
        player.addEntityToInventory(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }
// endregion
    
    @Test
    @Override
    public void TestIsNotBuildable_EmptyInventory() {
        SceptreEntity sceptre = new SceptreEntity();
        List<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        assertFalse(sceptre.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InventoryFullOfWrongItems() {
        SceptreEntity sceptre = new SceptreEntity();
        List<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        inventory.add(new ArrowsEntity());
        inventory.add(new BombEntity());
        assertFalse(sceptre.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InsufficientCorrectItems() {
        SceptreEntity sceptre = new SceptreEntity();
        List<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        inventory.add(new WoodEntity());
        inventory.add(new SunStoneEntity());
        assertFalse(sceptre.isBuildable(inventory));
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        SceptreEntity sceptre = new SceptreEntity();
        assertEntityResponseInfoEquals(sceptre, "sceptre-0-0-0", EntityTypes.SCEPTRE, new Position(0,0,0), false);
    }

    @Override
    @Test
    public void TestCollect() {
        SceptreEntity sceptre = new SceptreEntity();
        assertEntityIsCollected(sceptre);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        //  TODO A character with a sceptre does not need to bribe mercenaries or assassins to become allies, as they can use the sceptre to control their minds. The effects only last for 10 ticks.
    }

    @Test
    public void TestBuildSceptreHasBoth() {
        // TODO have many of the items required to build this and assert that you only use one set
    }

    @Test
    @Override
    public void TestOnlyUsesResourcesFromOneRecipe() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new ArrowsEntity());
        player.addEntityToInventory(new ArrowsEntity());
        player.addEntityToInventory(new WoodEntity());
        player.addEntityToInventory(new KeyEntity(1));
        player.addEntityToInventory(new TreasureEntity());
        player.addEntityToInventory(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.ARROW, EntityTypes.ARROW, EntityTypes.KEY, EntityTypes.SCEPTRE), player);
    }
}
