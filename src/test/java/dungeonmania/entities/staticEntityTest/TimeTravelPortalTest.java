package dungeonmania.entities.staticEntityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.OlderCharacter;
import dungeonmania.entities.staticEntities.TimeTravelPortalEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TimeTravelPortalTest implements IEntityTests, IInteractingEntityTest {

    // Test 1: create a dungeon and save the details of a json-- similar saveGame
    // Test 2: load from the expected json
    // Test 3: make a dungeon with some stuff-- 5 ticks rewind and then 5 ticks
    // Test 4: yeet himself contact with player
    // Test 5: yeet himself using object doesnt have
    // Test 6: yeet himself when you try to move somewhere but you get blocked

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

    /*@Test
    public void TestLoadFromTimeTravel() {
        Dungeon d = getDungeonForTimeTravel();
        JsonArray start = d.timeTravelSave();
        d.tick(Direction.UP);
        d.tick(Direction.DOWN);
        d.tick(Direction.LEFT);
        d.tick(Direction.RIGHT);
        assertEquals(start, d.loadJsonState(5));
    }*/

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
        assertEquals(d.entitiesControl.getEntitiesOfType(OlderCharacter.class).size(), 1);
        d.tick(Direction.UP);
        d.tick(Direction.UP);
        assertEquals(player.getPosition(), new Position(0, 2));
        assertEquals(d.entitiesControl.getEntities().size(), 0);
    }

    @Test
    public void TestUseObjectDoesntExist() {
        CharacterEntity player = new CharacterEntity(0, 0);
        BombEntity bomb = new BombEntity(0, 3);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(bomb);
        Dungeon d = new Dungeon(entities, "Standard", player);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(Direction.DOWN);
        d.tick(bomb.getId());
        d.tick(Direction.DOWN);
        d.timeTravel(6);
        assertEquals(d.entitiesControl.getEntitiesOfType(OlderCharacter.class).size(), 1);
        assertEquals(player.getPosition(), new Position(0, 4));
        d.tick(Direction.UP);
        d.tick(Direction.RIGHT);
        d.tick(Direction.RIGHT); 
        assertThrows(InvalidActionException.class, () -> d.tick(Direction.RIGHT));
        assertEquals(d.entitiesControl.getEntitiesOfType(OlderCharacter.class).size(), 0);
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
        BombEntity bomb = new BombEntity(0, 1);
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



    private Dungeon getDungeonForTimeTravel() {
        /*
            * 0 1 2 3 0 . . . . 1 X W . . 2 W S B P 3 W O I . 4 X A . . 5 X . . T
            */

        String entities = "{\"entities\": [" + "{\"x\": 0,\"y\": 1,\"type\": \"spider\"}," + // X 0
                "{\"x\": 1,\"y\": 1,\"type\": \"wall\"}," + // W 1

                "{\"x\": 0,\"y\": 2,\"type\": \"wall\"}," + // W 2
                "{\"x\": 1,\"y\": 2,\"type\": \"switch\"}," + // S 3
                "{\"x\": 2,\"y\": 2,\"type\": \"boulder\"}," + // B 4
                "{\"x\": 3,\"y\": 2,\"type\": \"player\"}," + // P

                "{\"x\": 0,\"y\": 3,\"type\": \"wall\"}," + // W 5
                "{\"x\": 1,\"y\": 3,\"type\": \"bomb\"}," + // O 6
                "{\"x\": 1,\"y\": 3,\"type\": \"switch\"}," + // S 7
                "{\"x\": 2,\"y\": 3,\"type\": \"wood\"}," + // I 8

                "{\"x\": 0,\"y\": 4,\"type\": \"spider\"}," + // X 9
                "{\"x\": 1,\"y\": 4,\"type\": \"arrow\"}," + // A 10

                "{\"x\": 0,\"y\": 5,\"type\": \"spider\"}," + // X 11
                "{\"x\": 3,\"y\": 5,\"type\": \"exit\"}" + // T 12

                "]}";

        String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
        JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
        JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
        return new Dungeon(entitiesJson, goalsJson, "Standard", "", "");
    }
}
