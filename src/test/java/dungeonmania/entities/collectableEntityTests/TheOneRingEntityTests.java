package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.collectableEntities.TheOneRingEntity;
import dungeonmania.util.Position;

public class TheOneRingEntityTests implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        TheOneRingEntity ring = new TheOneRingEntity(0,0,0);
        assertEntityResponseInfoEquals(
            one_ring,
            "one_ring-0-0-0",
            "one_ring",
            new Position(0,0,0),
            false
        );
    }

    @Test
    public void TestCollect() {
        TheOneRingEntity one_ring = new TheOneRingEntity(0,0,0);
        assertEntityIsCollected(one_ring);
    }

    
    @Override
    @Test

    public void TestUseCollectable() {
       //TO Do: need to implement 
       
    }
    
     
}

    
}
