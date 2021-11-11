package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dungeonmania.util.Position;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.SunStoneEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

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
    //The SunStone should not be removed from inventory after use 
    public void TestUseCollectable() {
        CharacterEntity player = new CharacterEntity();
        SunStoneEntity sun_stone = new SunStoneEntity(0,0);
        sun_stone.used(player);
        assertFalse(sun_stone.isPlacedAfterUsing());
    }
}
