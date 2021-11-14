package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.Logic;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.OlderCharacter;
import dungeonmania.entities.staticEntities.TimeTravelPortalEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TimeTravelPortalTest implements IEntityTests, IInteractingEntityTest {
    @Override
    @Test
    public void TestInteraction() {
        CharacterEntity player = new CharacterEntity(0, 0);
        TimeTravelPortalEntity portal = new TimeTravelPortalEntity(0, 1);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntity(portal);
        player.move(Direction.DOWN, entities);
        assertTrue(player.IsTimeTravelling());
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        TimeTravelPortalEntity portal = new TimeTravelPortalEntity();
        assertEntityResponseInfoEquals(
            portal,
            "time_travelling_portal-0-0-0",
            EntityTypes.TIME_TRAVEL_PORTAL,
            new Position(0,0,0),
            false
        );
    }

    @Test
    public void TestFightBetweenNewAndOld() {
        CharacterEntity player = new CharacterEntity(0, 0);
        Dungeon d = new Dungeon(new ArrayList<>(), "Standard", player);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.timeTravel(5);
        player.setHealth(200);
        assertEquals(player.getPosition(), new Position(0, 4));
        assertEquals(1, d.entitiesControl.getEntitiesOfType(OlderCharacter.class).size());
        d.tick(Direction.UP);
        d.tick(Direction.UP);
        assertEquals(player.getPosition(), new Position(0, 2));
        assertEquals(0, d.entitiesControl.getEntities().size());
    }

    @Test
    public void TestUseObjectDoesntExist() {
        CharacterEntity player = new CharacterEntity(0, 0);
        BombEntity bomb = new BombEntity(0, 3, Logic.ANY);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(bomb);
        Dungeon d = new Dungeon(entities, "Standard", player);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(bomb.getId());
        d.tick(Direction.DOWN);
        d.timeTravel(6);
        assertEquals(1, d.entitiesControl.getEntitiesOfType(OlderCharacter.class).size());
        assertEquals(player.getPosition(), new Position(0, 4));
        d.tick(Direction.UP);
        d.tick(Direction.RIGHT);
        d.tick(Direction.RIGHT); 
        assertThrows(InvalidActionException.class, () -> d.tick(Direction.RIGHT));
        assertEquals(0, d.entitiesControl.getEntitiesOfType(OlderCharacter.class).size());
    }

    @Test
    public void TestUseCase() {
        CharacterEntity player = new CharacterEntity(0, 0);
        BoulderEntity boulder = new BoulderEntity(1, 2);
        TimeTravelPortalEntity portal = new TimeTravelPortalEntity(2, 2);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(portal);
        Dungeon d = new Dungeon(entities, "Standard", player);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.RIGHT);
        d.tick(Direction.RIGHT);
        d.tick(Direction.UP);
        assertEquals(player.getPosition(), new Position(2, 2));
        OlderCharacter olderCharacter = (OlderCharacter) d.entitiesControl.getFirstEntityOfType(OlderCharacter.class);
        d.tick(Direction.LEFT);
        assertEquals(player.getPosition(), new Position(1, 2));
        for (int i = 0; i < 6; i++) {
            d.tick(Direction.NONE);
        }
        assertFalse(d.entitiesControl.contains(olderCharacter));
    }

    @Test
    public void TestBlock() {
        CharacterEntity player = new CharacterEntity(0, 0);
        BombEntity bomb = new BombEntity(0, 1, Logic.ANY);
        TimeTravelPortalEntity portal = new TimeTravelPortalEntity(2, 2);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(bomb);
        entities.add(portal);
        Dungeon d = new Dungeon(entities, "Standard", player);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.RIGHT);
        d.tick(Direction.RIGHT);
        d.tick(Direction.UP);
        assertEquals(player.getPosition(), new Position(2, 2));
        OlderCharacter olderCharacter = (OlderCharacter) d.entitiesControl.getFirstEntityOfType(OlderCharacter.class);
        assertEquals(olderCharacter.getPosition(), new Position(0, 0));
        d.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(2, 3));
        player.useItem(bomb.getId(), d.entitiesControl);
        for (int i = 0; i < 5; i++) {
            d.tick(Direction.RIGHT);
        }
        assertEquals(olderCharacter.getPosition(), new Position(1, 2));
        d.tick(Direction.NONE);
        assertFalse(d.entitiesControl.contains(olderCharacter));
    }
}