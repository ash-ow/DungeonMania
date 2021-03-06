package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.PortalEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalEntityTest implements IEntityTests, IInteractingEntityTest{
    @Override
    @Test
    public void TestEntityResponseInfo() {
        PortalEntity portal = new PortalEntity("BLUE");
        assertEntityResponseInfoEquals(
            portal,
            "portal-0-0-0",
            EntityTypes.PORTAL,
            new Position(0,0,0),
            false
        );
    }

    @Override
    @Test
    public void TestInteraction() {
        CharacterEntity player = new CharacterEntity(0, 0);
        PortalEntity portal1 = new PortalEntity(0, 1, "BLUE");
        PortalEntity portal2 = new PortalEntity(5, 1, "BLUE");
        EntitiesControl entities = new EntitiesControl();
        entities.addEntity(portal1);
        entities.addEntity(portal2);
        player.move(Direction.DOWN, entities);
        assertEquals(new Position(5, 1), player.getPosition());
        player.move(Direction.DOWN, entities);
        player.move(Direction.UP, entities);
        assertEquals(new Position(0, 1), player.getPosition());
    }

    @Test
    public void TestPortalBlocked() {
        CharacterEntity player = new CharacterEntity(0, 0);
        PortalEntity portal1 = new PortalEntity(0, 1, "BLUE");
        PortalEntity portal2 = new PortalEntity(5, 1, "BLUE");
        WallEntity wall = new WallEntity(5, 2);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntity(portal1);
        entities.addEntity(portal2);
        entities.addEntity(wall);
        player.move(Direction.DOWN, entities);
        assertEquals(new Position(5, 1), player.getPosition());
    }
}
