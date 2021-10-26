package dungeonmania.entities.staticEntityTest;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.util.Position;

public class SwitchEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        SwitchEntity switchEntity = new SwitchEntity();
        assertEntityResponseInfoEquals(
            switchEntity,
            "switch-0-0-0",
            "switch",
            new Position(0,0,0),
            false
        );
    }
}
