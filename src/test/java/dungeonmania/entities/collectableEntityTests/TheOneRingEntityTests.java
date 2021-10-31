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
       /*  
        player.setHealth(0);
        one_ring.used(player);
        assertEquals(100, player.getHealth());
        */
    }
    
     
}
