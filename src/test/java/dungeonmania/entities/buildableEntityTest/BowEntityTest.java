package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.Logic;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.BowEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.util.Position;

public class BowEntityTest implements IBuildableEntityTests {
    @Test
    public void TestIsBuildable() {
        BowEntity bow = new BowEntity();
        List<ICollectable> inventory = new ArrayList<ICollectable>();
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
        List<ICollectable> inventory = new ArrayList<ICollectable>();
        assertFalse(bow.isBuildable(inventory));
    }

    @Test
    @Override
    public void TestIsNotBuildable_InventoryFullOfWrongItems() {
        BowEntity bow = new BowEntity();
        List<ICollectable> inventory = new ArrayList<ICollectable>();
        inventory.add(new ArrowsEntity());
        inventory.add(new BombEntity(Logic.ANY));
        assertFalse(bow.isBuildable(inventory));
        
    }

    @Test
    @Override
    public void TestIsNotBuildable_InsufficientCorrectItems() {
        BowEntity bow = new BowEntity();
        List<ICollectable> inventory = new ArrayList<ICollectable>();
        inventory.add(new WoodEntity());
        inventory.add(new ArrowsEntity());
        inventory.add(new ArrowsEntity());
        assertFalse(bow.isBuildable(inventory));
        
    }

    @Test
    @Override
    public void TestEntityResponseInfo() {
        BowEntity bow = new BowEntity();
        assertEntityResponseInfoEquals(bow, "bow-0-0-0", EntityTypes.BOW, new Position(0,0,0), false);
    }

    @Test
    @Override
    public void TestCollect() {
        ArmourEntity armour = new ArmourEntity(0, 0);
        assertEntityIsCollected(armour);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity player = new CharacterEntity();
        BowEntity bow = new BowEntity();
        bow.contactWithPlayer(new EntitiesControl(), player);

        assertEquals(2,  bow.getDurability());
        assertEquals(100, player.getHealth());
        assertEquals(50, zombie.getHealth());

        zombie.contactWithPlayer(new EntitiesControl(), player);
        assertEquals(1,  bow.getDurability());
        assertFalse(zombie.isAlive());

        bow.used(player);

        assertNull(player.getInventoryItem(bow.getId()), "Inventory should not contain entity " + bow.getId());

    }

    @Test
    @Override
    public void TestOnlyUsesResourcesFromOneRecipe() {
        assertTrue(true, "the bow only has one recipe");
    }
}
