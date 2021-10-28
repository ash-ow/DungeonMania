package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class SwordEntityTest implements IEntityTests{
    @Override
    @Test
    public void TestEntityResponseInfo() {
        SwordEntity sword = new SwordEntity(0,3,0);
        assertEntityResponseInfoEquals(sword, "sword-0-3-0", "sword", new Position(0,3), false);
    }
    
    @Test
    public void TestSwordCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SwordEntity sword = new SwordEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(sword);
        assertTrue(entities.getEntities().size() == 1);

        sword.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "sword");
        }
    }
}
