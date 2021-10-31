package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;

public class OneRingEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        OneRingEntity ring = new OneRingEntity(0,0,0);
        assertEntityResponseInfoEquals(
            ring,
            "one_ring-0-0-0",
            "one_ring",
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestCollect() {
        OneRingEntity ring = new OneRingEntity(0,0,0);
        assertEntityIsCollected(ring);
    }

    @Test
    public void TestRespawn() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new OneRingEntity());
        player.setHealth(0);
        player.isAlive();
        assertEquals(100, player.getHealth());
        List<ICollectableEntity> inventory = player.getInventory();
        assertNull(EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class));
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }
    
}
