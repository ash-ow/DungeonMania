package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwordEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        SwordEntity sword = new SwordEntity(0, 0, 0);
        assertEntityResponseInfoEquals(sword, "sword-0-0-0", "sword", new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        SwordEntity sword = new SwordEntity(0, 0, 0);
        sword.setDurability(2);
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.addEntities(sword);
        sword.contactWithPlayer(entities, player);
        sword.used(player);
        
        assertNotNull(player.getInventoryItem(sword.getId()), "Inventory should contain entity " + sword.getId());
        assertEquals(1, sword.getDurability());
        assertEntityIsUsed(sword);    
    }

    @Test
    @Override
    public void TestCollect() {
        SwordEntity sword = new SwordEntity(0,0,0);
        assertEntityIsCollected(sword);
    }
}
