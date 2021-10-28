package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.TreasureEntity;

import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class TreasureEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        TreasureEntity treasure = new TreasureEntity(0,0,0);
        assertEntityResponseInfoEquals(
            treasure,
            "treasure-0-0-0",
            "treasure",
            new Position(0,0),
            false
        );
    }

    @Test
    @Override
    public void TestCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        TreasureEntity treasure = new TreasureEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(treasure);
        assertEquals(1, entities.getEntities().size());

        treasure.interactWithPlayer(entities, Direction.RIGHT, player);

        assertEquals(1, player.getInventoryInfo().size());
        assertEquals(0, entities.getEntities().size());

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "treasure");
        }
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }
}