package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class ArmourEntityTest implements IEntityTests {
    
    @Override
    @Test
    public void TestEntityResponseInfo() {
        ArmourEntity armour = new ArmourEntity(0,0,0);
        assertEntityResponseInfoEquals(armour, "armour-0-0-0", "armour", new Position(0,4), false);
    }
    
    @Test
    public void TestArmourCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArmourEntity armour = new ArmourEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(armour);
        assertTrue(entities.getEntities().size() == 1);

        armour.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() > 0);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "armour");
        }
    }
}
