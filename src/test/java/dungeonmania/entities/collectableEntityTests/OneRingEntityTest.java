package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;

public class OneRingEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        OneRingEntity ring = new OneRingEntity(0, 0);
        assertEntityResponseInfoEquals(
            ring,
            "one_ring-0-0-0",
            EntityTypes.ONE_RING,
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestCollect() {
        OneRingEntity ring = new OneRingEntity(0, 0);
        assertEntityIsCollected(ring);
    }

    @Override
    public void TestUseCollectable() {
        CharacterEntity player = new CharacterEntity();
        player.addEntityToInventory(new OneRingEntity());
        player.setHealth(0);
        player.isAlive();
        assertEquals(100, player.getHealth());
        List<ICollectable> inventory = player.getInventory();
        assertNull(EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class));
    }
}
