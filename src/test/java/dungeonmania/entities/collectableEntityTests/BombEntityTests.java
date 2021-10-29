package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BombEntityTests implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        BombEntity bomb = new BombEntity(0, 0, 0);
        assertEntityResponseInfoEquals(bomb, "bomb-0-0-0", "bomb", new Position(0,0), false);
    }

    @Override
    public void TestUseCollectable() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BombEntity bomb = new BombEntity(0,0,0);
        
        assertEquals(new Position(0, 0, 0), bomb.getPosition());

        player.addEntityToInventory(bomb);
        player.move(Direction.DOWN);
        player.useItem("bomb");

        assertEquals(new Position(0, 1, 0), bomb.getPosition(), "Bomb should be placed in the players new position");
        
        player.move(Direction.DOWN);
        player.move(Direction.UP);
        assertEquals(new Position(0, 2, 0), player.getPosition(), "Player should not be able to move back onto the bomb once it has been placed");
    }

    @Test
    @Override
    public void TestCollect() {
        BombEntity bomb = new BombEntity(0,0,0);
        assertEntityIsCollected(bomb);
    }
    
    @Test
    public void TestExplode() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(3, 2, 0); // P
        entities.add(player);

        /*
                0 1 2 3
            0   . . . .
            1   X W . .
            2   W S B P
            3   I O W .
            4   X A X .
        */

        // exploding entities
        BoulderEntity boulder = new BoulderEntity(2, 2, 0); // B
        entities.add(boulder);
        BombEntity bomb = new BombEntity(1,3,0); // O
        entities.add(bomb);
        SwitchEntity switches_onbomb = new SwitchEntity(1, 3, 1); // S
        entities.add(switches_onbomb);
        SpiderEntity spider_botleft = new SpiderEntity(0, 4, 0); // X
        entities.add(spider_botleft);
        SpiderEntity spider_botright = new SpiderEntity(2, 4, 0); // X
        entities.add(spider_botright);
        WallEntity wall_topleft = new WallEntity(0, 2, 0); // W
        entities.add(wall_topleft);
        WallEntity wall_right = new WallEntity(2, 3, 0); // W
        entities.add(wall_right);
        SwitchEntity switches_onboulder = new SwitchEntity(1, 2, 0); // S
        entities.add(switches_onboulder);
        WoodEntity wood = new WoodEntity(0, 3, 0); // I
        entities.add(wood);
        ArrowsEntity arrows = new ArrowsEntity(1, 4, 0); // A
        entities.add(arrows);
        WallEntity wall_upup = new WallEntity(1, 1, 0); // W
        entities.add(wall_upup);
        SpiderEntity spider_upupleft = new SpiderEntity(0,1, 0); // X
        entities.add(spider_upupleft);
        
        // Move the player left to push the boulder into the bomb
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.tick(Direction.LEFT);

        Assertions.assertAll(
                () -> assertTrue(dungeon.entitiesControl.contains(player), "Entity controller should still contain wood"),
                () -> assertTrue(dungeon.entitiesControl.contains(spider_upupleft), "Entity controller should contain spider_upupleft"),
                () -> assertTrue(dungeon.entitiesControl.contains(wall_upup), "Entity controller should still contain wall_upup"),

                () -> assertFalse(dungeon.entitiesControl.contains(boulder), "Entity controller should no longer contain boulder"),
                () -> assertFalse(dungeon.entitiesControl.contains(bomb), "Entity controller should no longer contain bomb"),
                () -> assertFalse(dungeon.entitiesControl.contains(spider_botleft), "Entity controller should no longer contain spider_botleft"),
                () -> assertFalse(dungeon.entitiesControl.contains(spider_botright), "Entity controller should no longer contain spider_botright"),
                () -> assertFalse(dungeon.entitiesControl.contains(wall_topleft), "Entity controller should no longer contain wall_topleft"),
                () -> assertFalse(dungeon.entitiesControl.contains(wall_right), "Entity controller should no longer contain wall_right"),
                () -> assertFalse(dungeon.entitiesControl.contains(switches_onbomb), "Entity controller should no longer contain switches_onbomb"),
                () -> assertFalse(dungeon.entitiesControl.contains(switches_onboulder), "Entity controller should no longer contain switches_onboulder"),
                () -> assertFalse(dungeon.entitiesControl.contains(wood), "Entity controller should still contain wood"),
                () -> assertFalse(dungeon.entitiesControl.contains(arrows), "Entity controller should still contain arrows")
            );
        }

}
