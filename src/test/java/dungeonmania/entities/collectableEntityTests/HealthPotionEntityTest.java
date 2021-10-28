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
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        assertEntityIsCollected(health_potion);
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }
}
