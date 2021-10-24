package dungeonmania.entities.staticEntityTest;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.util.Position;

public class ExitEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        ExitEntity exit = new ExitEntity();
        assertEntityResponseInfoEquals(
            exit,
            "exit-0-0-0",
            "exit",
            new Position(0,0,0),
            false
        );
    }
}
