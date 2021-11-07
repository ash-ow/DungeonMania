package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.TimeTravelPortal;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TimeTravelPortalTest implements IEntityTests, IInteractingEntityTest {

    @Override
    @Test
    public void TestInteraction() {
        CharacterEntity player = new CharacterEntity(0, 0);
        TimeTravelPortal portal = new TimeTravelPortal(0, 1);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntity(portal);
        player.move(Direction.DOWN, entities);
        assertTrue(player.IsTimeTravelling());
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        TimeTravelPortal portal = new TimeTravelPortal();
        assertEntityResponseInfoEquals(
            portal,
            "time_travelling_portal-0-0-0",
            EntityTypes.TIME_TRAVEL_PORTAL,
            new Position(0,0,0),
            false
        );
    }
    
}
