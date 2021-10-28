package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.HealthPotionEntity;

import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class HealthPotionEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            health_potion,
            "health_potion-0-0-0",
            "health_potion",
            new Position(0,0),
            false
        );
    }

    @Test

    @Override
    public void TestCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(health_potion);
        assertEquals(1, entities.getEntities().size());

        health_potion.interactWithPlayer(entities, Direction.RIGHT, player);

        assertEquals(1, player.getInventoryInfo().size());
        assertEquals(0, entities.getEntities().size());

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "health_potion");
        }
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }
}
