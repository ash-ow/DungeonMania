package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
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
        MercenaryEntity merc = new MercenaryEntity();
        CharacterEntity player = new CharacterEntity();
        SwordEntity sword = new SwordEntity();
        sword.contactWithPlayer(new EntitiesControl(), player);

        assertEquals(5,  sword.getDurability());
        assertEquals(100, player.getHealth());
        assertEquals(70, merc.getHealth());

        merc.contactWithPlayer(new EntitiesControl(), player);
        // sword only gets used once = only one round of battle with sword
        assertEquals(4,  sword.getDurability()); 
        // without a sword, the player has 76 health - proof only one round is needed
        assertEquals(79, player.getHealth());
        assertFalse(merc.isAlive());

        sword.used(player);
        sword.used(player);
        sword.used(player);
        sword.used(player);

        assertNull(player.getInventory().getInventoryItemById(sword.getId()), "Inventory should not contain entity " + sword.getId());
    }

    @Test
    @Override
    public void TestCollect() {
        SwordEntity sword = new SwordEntity(0, 0);
        assertEntityIsCollected(sword);
    }
}
