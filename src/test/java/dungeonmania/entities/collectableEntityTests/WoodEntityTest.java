package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.util.Position;

public class WoodEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        WoodEntity wood = new WoodEntity(0, 0, 0);
        assertEntityResponseInfoEquals(wood, "wood-0-0-0", EntityTypes.WOOD, new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        WoodEntity wood = new WoodEntity(0, 0, 0);
        assertEntityIsUsedAndPlacedIfApplicable(wood);
    }

    @Test
    @Override
    public void TestCollect() {
        WoodEntity wood = new WoodEntity(0,0,0);
        assertEntityIsCollected(wood);
    }
}
