package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;
import dungeonmania.entities.IEntityTests;

public class ArrowsEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        ArrowsEntity arrow = new ArrowsEntity(0,1,0);
        assertEntityResponseInfoEquals(arrow, "arrow-0-1-0", "arrow", new Position(0,1), false);
    }
    
    @Test
    public void TestArrowsCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArrowsEntity arrows = new ArrowsEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(arrows);
        assertTrue(entities.getEntities().size() == 1);

        arrows.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "arrow");
        }
    }
}
