package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

        assertEquals(new Position(0, 1, 0), bomb.getPosition());
    }

    @Test
    @Override
    public void TestCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BombEntity bomb = new BombEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(bomb);
        assertEquals(1, entities.getEntities().size());

        bomb.interactWithPlayer(entities, Direction.RIGHT, player);

        assertEquals(1, player.getInventoryInfo().size());
        assertEquals(0, entities.getEntities().size());

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "bomb");
        }
    }
    
    public void TestExplode() {
        ArrayList<IEntity> entities = new ArrayList<>();

        /*
                0 1 2 3
            0   . . . .
            1   . W . .
            2   W S B P
            3   I O . .
            4   X A . .
        */

        // exploding entities
        BoulderEntity boulder = new BoulderEntity(2, 2, 0); // B
        entities.add(boulder);
        BombEntity bomb = new BombEntity(1,3,0); // O
        entities.add(bomb);
        SpiderEntity spider = new SpiderEntity(0, 4, 0); // X
        entities.add(spider);
        WallEntity wall = new WallEntity(0, 2, 0); // W
        entities.add(wall);
        SwitchEntity switches = new SwitchEntity(1, 2, 0); // S
        entities.add(switches);

        // non exploding entities
        CharacterEntity player = new CharacterEntity(3, 2, 0); // P
        entities.add(player);
        WoodEntity wood = new WoodEntity(0, 3, 0); // I
        entities.add(wood);
        ArrowsEntity arrows = new ArrowsEntity(1, 4, 0); // A
        entities.add(arrows);
        WallEntity wall2 = new WallEntity(1, 1, 0); // W
        entities.add(wall2);
        
        // Move the player left to push the boulder into the bomb
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.LEFT);

        assertFalse(entities.contains(boulder), "Entity controller should no longer contain boulder");
        assertFalse(entities.contains(bomb), "Entity controller should no longer contain bomb");
        assertFalse(entities.contains(spider), "Entity controller should no longer contain spider");
        assertFalse(entities.contains(wall), "Entity controller should no longer contain wall");
        assertFalse(entities.contains(switches), "Entity controller should no longer contain switches");

        assertTrue(entities.contains(player), "Entity controller should still contain wood");
        assertTrue(entities.contains(wood), "Entity controller should still contain wood");
        assertTrue(entities.contains(arrows), "Entity controller should still contain arrows");
        assertTrue(entities.contains(wall2), "Entity controller should still contain wall2");
    }

}
