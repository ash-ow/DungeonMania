package dungeonmania.entities.staticEntityTest;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.staticEntities.DoorEntity;
import dungeonmania.util.Position;

public class DoorEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        DoorEntity door = new DoorEntity();
        assertEntityResponseInfoEquals(
            door,
            "door-0-0-0",
            "door",
            new Position(0,0,0),
            false
        );
    }
}
