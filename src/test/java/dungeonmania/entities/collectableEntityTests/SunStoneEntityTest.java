package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.SunStoneEntity;

public class SunStoneEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        SunStoneEntity sun_stone = new SunStoneEntity(0,0);
        assertEntityResponseInfoEquals(
            sun_stone,
            "sun_stone-0-0-0",
            EntityTypes.SUN_STONE,
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestCollect() {
        SunStoneEntity sun_stone = new SunStoneEntity(0,0);
        assertEntityIsCollected(sun_stone);
    }

    @Override
    @Test
    public void TestUseCollectable() {
        // test to check see that it remains in inventory after use? 


    }



}
