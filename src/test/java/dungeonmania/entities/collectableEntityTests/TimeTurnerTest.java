package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.TimeTurnerEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;


public class TimeTurnerTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        TimeTurnerEntity timeTurner = new TimeTurnerEntity(0,0);
        assertEntityResponseInfoEquals(
            timeTurner,
            "time_turner-0-0-0",
            EntityTypes.TIME_TURNER,
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestCollect() {
        TimeTurnerEntity timeTurner = new TimeTurnerEntity(0,0);
        assertEntityIsCollected(timeTurner);
    }

    @Override
    @Test
    public void TestUseCollectable() {
        CharacterEntity player = new CharacterEntity(0, 0);
        TimeTurnerEntity timeTurner = new TimeTurnerEntity(0,0);
        timeTurner.used(player);
        assertTrue(player.IsTimeTravelling());
    }

}
