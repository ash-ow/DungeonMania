package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.util.Position;

public class ArrowsEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        ArrowsEntity arrows = new ArrowsEntity(0, 0, 0);
        assertEntityResponseInfoEquals(arrows, "arrow-0-0-0", "arrow", new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        ArrowsEntity arrow = new ArrowsEntity(0, 0, 0);
        assertEntityIsUsed(arrow);
    }

    @Test
    @Override
    public void TestCollect() {
        ArrowsEntity arrows = new ArrowsEntity(0,0,0);
        assertEntityIsCollected(arrows);
    }
}
