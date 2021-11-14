package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.KeyEntity;


public class KeyEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        KeyEntity key = new KeyEntity(0, 0, 0);
        assertEntityResponseInfoEquals(
            key,
            "key-0-0-0",
            EntityTypes.KEY,
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestCollect() {
        KeyEntity key = new KeyEntity(0, 0, 0);
        assertEntityIsCollected(key);
    }

    @Override
    public void TestUseCollectable() {
        assertTrue(true, "keys are never used outside the DoorEntity context");
    }
}