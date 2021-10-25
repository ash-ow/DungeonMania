package dungeonmania.entities.staticEntityTest;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.staticEntities.PortalEntity;
import dungeonmania.util.Position;

public class PortalEntityTest implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        PortalEntity portal = new PortalEntity();
        assertEntityResponseInfoEquals(
            portal,
            "portal-0-0-0",
            "portal",
            new Position(0,0,0),
            false
        );
    }
}
