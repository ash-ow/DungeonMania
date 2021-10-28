package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.KeyEntity;

import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class KeyEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        KeyEntity key = new KeyEntity(0,0,0);
        assertEntityResponseInfoEquals(
            key,
            "key-0-0-0",
            "key",
            new Position(0,0),
            false
        );
    }

    @Override
    public void TestCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        KeyEntity key = new KeyEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(key);
        assertEquals(1, entities.getEntities().size());

        key.interactWithPlayer(entities, Direction.RIGHT, player);

        assertEquals(1, player.getInventoryInfo().size());
        assertEquals(0, entities.getEntities().size());

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "key");
        }

    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }
}