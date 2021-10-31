package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ArmourEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        ArmourEntity armour = new ArmourEntity(0, 0, 0);
        assertEntityResponseInfoEquals(armour, "armour-0-0-0", "armour", new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        ArmourEntity armour = new ArmourEntity(0, 0, 0);
        armour.setDurability(2);
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.addEntity(armour);
        armour.contactWithPlayer(entities, player);
        armour.used(player);
        
        assertNotNull(player.getInventoryItem(armour.getId()), "Inventory should contain entity " + armour.getId());
        assertEquals(1, armour.getDurability());
        assertEntityIsUsed(armour);    
    }

    @Test
    @Override
    public void TestCollect() {
        ArmourEntity armour = new ArmourEntity(0,0,0);
        assertEntityIsCollected(armour);
    }
}
