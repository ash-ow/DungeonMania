package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.util.Position;

public class ArrowsEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        ArrowsEntity arrows = new ArrowsEntity(0, 0);
        assertEntityResponseInfoEquals(arrows, "arrow-0-0-0", EntityTypes.ARROW, new Position(0,0), false);
    }


    @Test
    @Override
    public void TestCollect() {
        ArrowsEntity arrows = new ArrowsEntity(0,0);
        assertEntityIsCollected(arrows);
    }


    @Override
    public void TestUseCollectable() {
        // arrows never used
    }
}
