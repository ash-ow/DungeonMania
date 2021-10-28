package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwordEntityTests implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        SwordEntity sword = new SwordEntity(0, 0, 0);
        assertEntityResponseInfoEquals(sword, "sword-0-0-0", "sword", new Position(0,0), false);
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }

    @Test
    @Override
    public void TestCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SwordEntity sword = new SwordEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(sword);
        assertEquals(1, entities.getEntities().size());

        sword.interactWithPlayer(entities, Direction.RIGHT, player);

        assertEquals(1, player.getInventoryInfo().size());
        assertEquals(0, entities.getEntities().size());

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "sword");
        }
    }
}
