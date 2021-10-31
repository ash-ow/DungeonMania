package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.util.Position;

public class SwordEntityTest implements ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        SwordEntity sword = new SwordEntity(0, 0, 0);
        assertEntityResponseInfoEquals(sword, "sword-0-0-0", EntityTypes.SWORD, new Position(0,0), false);
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }

    @Test
    @Override
    public void TestCollect() {
        SwordEntity sword = new SwordEntity(0,0,0);
        assertEntityIsCollected(sword);
    }
}
