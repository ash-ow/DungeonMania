package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.TreasureEntity;

public class TreasureEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        TreasureEntity treasure = new TreasureEntity(0,0,0);
        assertEntityResponseInfoEquals(
            treasure,
            "treasure-0-0-0",
            EntityTypes.TREASURE,
            new Position(0,0),
            false
        );
    }

    @Test
    @Override
    public void TestCollect() {
        TreasureEntity treasure = new TreasureEntity(0,0,0);
        assertEntityIsCollected(treasure);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        TreasureEntity treasure = new TreasureEntity(0, 0, 0);
        assertEntityIsUsedAndPlacedIfApplicable(treasure);
    }
}