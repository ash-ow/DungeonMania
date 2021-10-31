package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.RareCollectableEntity;
import dungeonmania.util.Position;

public class RareCollectableEntityTests implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        RareCollectableEntity ring = new RareCollectableEntity(0,0,0);
        assertEntityResponseInfoEquals(
            ring,
            "one_ring-0-0-0",
            EntityTypes.ONE_RING,
            new Position(0,0,0),
            false
        );
    }

    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void TestCollect() {
        // TODO Auto-generated method stub
        
    }
    
}
