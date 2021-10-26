package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.util.Direction;
import dungeonmania.response.models.ItemResponse;
import java.util.List;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class CollectableEntityTests implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        WoodEntity wood = new WoodEntity(0, 0, 0);
        assertEntityResponseInfoEquals(wood, "wood-0-0-0", "wood", new Position(0,0), false);

        ArrowsEntity arrows = new ArrowsEntity(0,1,0);
        assertEntityResponseInfoEquals(arrows, "arrows-0-1-0", "arrows", new Position(0,1), false);

        BombEntity bomb = new BombEntity(0,2,0);
        assertEntityResponseInfoEquals(bomb, "bomb-0-2-0", "bomb", new Position(0,2), false);

        SwordEntity sword = new SwordEntity(0,3,0);
        assertEntityResponseInfoEquals(sword, "sword-0-3-0", "sword", new Position(0,3), false);

        ArmourEntity armour = new ArmourEntity(0,4,0);
        assertEntityResponseInfoEquals(armour, "armour-0-4-0", "armour", new Position(0,4), false);
    }
    
    @Test
    public void TestWoodCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        WoodEntity wood = new WoodEntity(0,1,0);
        EntitiesControl entities = new EntitiesControl();
        assertEntityResponseInfoEquals(wood, "wood-0-1-0", "wood", new Position(0,1), false);
        
        entities.addEntities(wood);
        assertTrue(entities.getEntities().size() == 1);

        wood.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() > 0);
        assertTrue(entities.getEntities().size() == 0);
    }

    @Test
    public void TestArrowsCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArrowsEntity arrows = new ArrowsEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        assertEntityResponseInfoEquals(arrows, "arrows-0-0-0", "arrows", new Position(0,0), false);
        
        entities.addEntities(arrows);
        assertTrue(entities.getEntities().size() == 1);

        arrows.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() > 0);
        assertTrue(entities.getEntities().size() == 0);
    }

    @Test
    public void TestBombCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BombEntity bomb = new BombEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        assertEntityResponseInfoEquals(bomb, "bomb-0-0-0", "bomb", new Position(0,0), false);
        
        entities.addEntities(bomb);
        assertTrue(entities.getEntities().size() == 1);

        bomb.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() > 0);
        assertTrue(entities.getEntities().size() == 0);
    }

    @Test
    public void TestSwordCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SwordEntity sword = new SwordEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        assertEntityResponseInfoEquals(sword, "sword-0-0-0", "sword", new Position(0,0), false);
        
        entities.addEntities(sword);
        assertTrue(entities.getEntities().size() == 1);

        sword.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() > 0);
        assertTrue(entities.getEntities().size() == 0);
    }

    @Test
    public void TestArmourCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArmourEntity armour = new ArmourEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        assertEntityResponseInfoEquals(armour, "armour-0-0-0", "armour", new Position(0,0), false);
        
        entities.addEntities(armour);
        assertTrue(entities.getEntities().size() == 1);

        armour.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() > 0);
        assertTrue(entities.getEntities().size() == 0);
    }

}


