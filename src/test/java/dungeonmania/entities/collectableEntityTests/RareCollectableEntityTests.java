package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.collectableEntities.RareCollectableEntity;
import dungeonmania.util.Position;

public class RareCollectableEntityTests extends CollectableEntityTests {

    @Override
    @Test
    public void TestEntityResponseInfo() {
        RareCollectableEntity key = new RareCollectableEntity(0,0,0);
        assertEntityResponseInfoEquals(
            key,
            "one_ring-0-0-0",
            "one_ring",
            new Position(0,0),
            false
        );
    }
    
}
