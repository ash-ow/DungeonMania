package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.Logic;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.SceptreEntity;
import dungeonmania.entities.movingEntities.AssassinEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.Inventory;
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
        player.getInventoryItems().add(new WoodEntity());
        player.getInventoryItems().add(new KeyEntity(1));
        player.getInventoryItems().add(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }

    public void assertCanBuildSceptreWithWoodTreasureSunStone() {
        CharacterEntity player = new CharacterEntity();
        player.getInventoryItems().add(new WoodEntity());
        player.getInventoryItems().add(new TreasureEntity());
        player.getInventoryItems().add(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }

    public void assertCanBuildSceptreWith2ArrowsTreasureSunStone() {
        CharacterEntity player = new CharacterEntity();
        player.getInventoryItems().add(new ArrowsEntity());
        player.getInventoryItems().add(new ArrowsEntity());
        player.getInventoryItems().add(new TreasureEntity());
        player.getInventoryItems().add(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }

    public void assertCanBuildSceptreWith2ArrowsKeySunStone() {
        CharacterEntity player = new CharacterEntity();
        player.getInventoryItems().add(new ArrowsEntity());
        player.getInventoryItems().add(new ArrowsEntity());
        player.getInventoryItems().add(new KeyEntity(1));
        player.getInventoryItems().add(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.SCEPTRE), player);
    }
// endregion
    
    @Test
    @Override
    public void TestIsNotBuildable_EmptyInventory() {
        SceptreEntity sceptre = new SceptreEntity();
        List<ICollectable> inventory = new ArrayList<ICollectable>();
        assertFalse(sceptre.isBuildable(new Inventory(inventory)));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InventoryFullOfWrongItems() {
        SceptreEntity sceptre = new SceptreEntity();
        List<ICollectable> inventory = new ArrayList<ICollectable>();
        inventory.add(new ArrowsEntity());
        inventory.add(new BombEntity(Logic.ANY));
        assertFalse(sceptre.isBuildable(new Inventory(inventory)));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InsufficientCorrectItems() {
        SceptreEntity sceptre = new SceptreEntity();
        List<ICollectable> inventory = new ArrayList<ICollectable>();
        inventory.add(new WoodEntity());
        inventory.add(new SunStoneEntity());
        assertFalse(sceptre.isBuildable(new Inventory(inventory)));
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
        CharacterEntity player = new CharacterEntity(0, 0);
        SceptreEntity sceptre = new SceptreEntity();
        player.getInventoryItems().add(sceptre);
        ArrayList<IEntity> entities = new ArrayList<>();
        AssassinEntity mercenary = new AssassinEntity(0, 1);
        entities.add(mercenary);
        Dungeon dungeon = new Dungeon(entities, "standard", player);
        dungeon.tick(sceptre.getId());
        assertTrue(mercenary.isBribed());
        for (int i = 0; i < 10; i++) {
            dungeon.tick(Direction.NONE);
        }
        assertFalse(mercenary.isBribed());
    }

    @Test
    public void TestBuildSceptreHasBoth() {
        // TODO have many of the items required to build this and assert that you only use one set
    }

    @Test
    @Override
    public void TestOnlyUsesResourcesFromOneRecipe() {
        CharacterEntity player = new CharacterEntity();
        player.getInventoryItems().add(new ArrowsEntity());
        player.getInventoryItems().add(new ArrowsEntity());
        player.getInventoryItems().add(new WoodEntity());
        player.getInventoryItems().add(new KeyEntity(1));
        player.getInventoryItems().add(new TreasureEntity());
        player.getInventoryItems().add(new SunStoneEntity());
        player.build(EntityTypes.SCEPTRE);
        assertInventoryTypesAsExpected(Arrays.asList(EntityTypes.ARROW, EntityTypes.ARROW, EntityTypes.KEY, EntityTypes.SCEPTRE), player);
    }
}
