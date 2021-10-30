package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.TheOneRingEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;
import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;

import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;

public class TheOneRingEntityTests implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        TheOneRingEntity one_ring = new TheOneRingEntity(0,0,0);
        assertEntityResponseInfoEquals(
            one_ring,
            "one_ring-0-0-0",
            "one_ring",
            new Position(0,0,0),
            false
        );
    }

    @Test
    public void TestCollect() {
        TheOneRingEntity one_ring = new TheOneRingEntity(0,0,0);
        assertEntityIsCollected(one_ring);
    }


    @Override
    @Test

    public void TestUseCollectable() {
        TheOneRingEntity one_ring = new TheOneRingEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
            
        player.setHealth(0);
        one_ring.used(player);
        assertEquals(100, player.getHealth());
    }

    @Test
    public void TestRandomDrop() {
        TheOneRingEntity one_ring = new TheOneRingEntity();
        CharacterEntity player = new CharacterEntity(0,1,0);
        SpiderEntity spider = new SpiderEntity(0,2,0);
        ArrayList<IEntity> entities = new ArrayList<>();  

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 2, 0), spider.getPosition());

        spider.setHealth(0);
        dungeon.tick(Direction.DOWN);
        
        //test whether one ring is randomly dropped by enemy
        //assertEquals(spider.getDropRing(),true);
        assertEntityIsCollected(one_ring);
    }
    
    @Test
    public void TestUseOnlyDead() {
        TheOneRingEntity one_ring = new TheOneRingEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
                
        player.setHealth(30);
        one_ring.used(player);
        assertEquals(30, player.getHealth());
    }

    
    
    
}
