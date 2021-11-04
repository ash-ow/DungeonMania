package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.util.Position;

public class SwordEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        SwordEntity sword = new SwordEntity(0, 0);
        assertEntityResponseInfoEquals(sword, "sword-0-0-0", EntityTypes.SWORD, new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity player = new CharacterEntity();
        SwordEntity sword = new SwordEntity();
        sword.contactWithPlayer(new EntitiesControl(), player);

        assertEquals(5,  sword.getDurability());
        assertEquals(100, player.getHealth());
        assertEquals(50, zombie.getHealth());

        zombie.contactWithPlayer(new EntitiesControl(), player);
        assertEquals(4,  sword.getDurability());
        assertFalse(zombie.isAlive());

        sword.used(player);
        sword.used(player);
        sword.used(player);
        sword.used(player);

        assertNull(player.getInventoryItem(sword.getId()), "Inventory should not contain entity " + sword.getId());
    }

    @Test
    @Override
    public void TestCollect() {
        SwordEntity sword = new SwordEntity(0, 0);
        assertEntityIsCollected(sword);
    }
}
