package dungeonmania.entities.staticEntityTest;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Position;

public class WallEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        WallEntity wall = new WallEntity();
        assertEntityResponseInfoEquals(
            wall,
            "wall-0-0-0",
            "wall",
            new Position(0,0,0),
            false
        );
    }
}
