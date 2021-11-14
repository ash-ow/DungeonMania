package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlockerTest;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.Logic;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BombEntityTest implements IBlockerTest, ICollectableEntityTest {
    @Test
    @Override
    public void TestEntityResponseInfo() {
        BombEntity bomb = new BombEntity(0, 0, Logic.ANY);
        assertEntityResponseInfoEquals(bomb, "bomb-0-0-0", EntityTypes.BOMB, new Position(0,0), false);
    }

    @Test
    @Override
    public void TestUseCollectable() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0);
        BombEntity bomb = new BombEntity(0, 1, Logic.ANY);
        entities.add(bomb);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);

        dungeon.tick(Direction.DOWN);
        assertItemInInventory("bomb-0-1-0", player, dungeon.entitiesControl);
        assertEquals(new Position(0, 1, 0), player.getPosition());

        dungeon.tick("bomb-0-1-0");
        assertEquals(new Position(0, 1, 0), dungeon.entitiesControl.getEntityById("bomb-0-1-0").getPosition(),
                "Bomb should be placed in the players new position");
        assertTrue(bomb.isArmed(), "Bomb should be active");
        assertItemNotInInventory("bomb-0-1-0", player, dungeon.entitiesControl, true);
    }

    @Test
    @Override
    public void TestCollect() {
        BombEntity bomb = new BombEntity(0, 0, Logic.ANY);
        assertEntityIsCollected(bomb);
    }
    
// region TestExplode
    
    @Test
    public void TestExplode() {
        // Move the player left to push the boulder into the bomb
        Dungeon dungeon = getDungeonWithBombTestData();
        dungeon.tick(Direction.LEFT);

        Assertions.assertAll( "Before the bomb is armed, nothing will explode",
            () -> assertEntityStillOnMap( dungeon, "0",  EntityTypes.TREASURE),
            () -> assertEntityStillOnMap( dungeon, "1",  EntityTypes.WALL),
            () -> assertEntityStillOnMap( dungeon, "2",  EntityTypes.WALL),
            () -> assertEntityStillOnMap( dungeon, "3",  EntityTypes.SWITCH),
            () -> assertEntityStillOnMap( dungeon, "4",  EntityTypes.BOULDER),
            () -> assertEntityStillOnMap( dungeon, "5",  EntityTypes.WALL),
            () -> assertEntityStillOnMap( dungeon, "6",  EntityTypes.BOMB),
            () -> assertEntityStillOnMap( dungeon, "7",  EntityTypes.SWITCH),
            () -> assertEntityStillOnMap( dungeon, "8",  EntityTypes.WOOD),
            () -> assertEntityStillOnMap( dungeon, "9",  EntityTypes.TREASURE),
            () -> assertEntityStillOnMap( dungeon, "11", EntityTypes.TREASURE),
            () -> assertEntityStillOnMap( dungeon, "10", EntityTypes.ARROW),
            () -> assertEntityStillOnMap( dungeon, "12", EntityTypes.EXIT),

            () -> assertNotNull( dungeon.getPlayer())
        );

        // Teleport the player to the bomb to arm it, then force a tick
        dungeon.tick(Direction.DOWN);
        dungeon.tick(Direction.LEFT);
        dungeon.tick("6");

        Assertions.assertAll( "Once the bomb is armed, it will explode",
            () -> assertEntityStillOnMap( dungeon, "0",  EntityTypes.TREASURE),
            () -> assertEntityStillOnMap( dungeon, "1",  EntityTypes.WALL),
            () -> assertEntityNotOnMap( dungeon, "2",  EntityTypes.WALL),
            () -> assertEntityNotOnMap(   dungeon, "3",  EntityTypes.SWITCH),
            () -> assertEntityNotOnMap(   dungeon, "4",  EntityTypes.BOULDER),
            () -> assertEntityNotOnMap(   dungeon, "5",  EntityTypes.WALL),
            () -> assertEntityNotOnMap(   dungeon, "6",  EntityTypes.BOMB),
            () -> assertEntityNotOnMap(   dungeon, "7",  EntityTypes.SWITCH),
            () -> assertItemInInventory("8", dungeon.getPlayer(), dungeon.entitiesControl),
            () -> assertEntityNotOnMap( dungeon, "9",    EntityTypes.TREASURE),
            () -> assertEntityNotOnMap( dungeon, "10",   EntityTypes.ARROW),
            () -> assertEntityStillOnMap( dungeon, "11", EntityTypes.TREASURE),
            () -> assertEntityStillOnMap( dungeon, "12", EntityTypes.EXIT),

            () -> assertNotNull( dungeon.getPlayer())
        );
    }

    public void assertEntityStillOnMap(Dungeon dungeon, String id, EntityTypes expectedType) {
        IEntity ent = dungeon.entitiesControl.getEntityById(id);
        assertEquals(expectedType, ent.getType(), "Entity type should be " + expectedType + " but was " + ent.getType());
        assertNotNull(ent, "Entity should still be on the map " + expectedType + " " + id);
    }

    public void assertEntityNotOnMap(Dungeon dungeon, String id, EntityTypes expectedType) {
        IEntity ent = dungeon.entitiesControl.getEntityById(id);
        assertNull(ent, "Entity should no longer be on the map " + expectedType + " " + id);
    }

    private Dungeon getDungeonWithBombTestData() {
        /*
           0 1 2 3 
        0  . . . . 
        1  X W . . 
        2  W S B P
        3  W O I .
        4  X A . .
        5  X . . T
        */

        String entities = "{\"entities\": [" + "{\"x\": 0,\"y\": 1,\"type\": \"treasure\"}," + // X 0
                "{\"x\": 1,\"y\": 1,\"type\": \"wall\"}," + // W 1

                "{\"x\": 0,\"y\": 2,\"type\": \"wall\"}," + // W 2
                "{\"x\": 1,\"y\": 2,\"type\": \"switch\"}," + // S 3
                "{\"x\": 2,\"y\": 2,\"type\": \"boulder\"}," + // B 4
                "{\"x\": 3,\"y\": 2,\"type\": \"player\"}," + // P

                "{\"x\": 0,\"y\": 3,\"type\": \"wall\"}," + // W 5
                "{\"x\": 1,\"y\": 3,\"type\": \"bomb\"}," + // O 6
                "{\"x\": 1,\"y\": 3,\"type\": \"switch\"}," + // S 7
                "{\"x\": 2,\"y\": 3,\"type\": \"wood\"}," + // I 8

                "{\"x\": 0,\"y\": 4,\"type\": \"treasure\"}," + // X 9
                "{\"x\": 1,\"y\": 4,\"type\": \"arrow\"}," + // A 10

                "{\"x\": 0,\"y\": 5,\"type\": \"treasure\"}," + // X 11
                "{\"x\": 3,\"y\": 5,\"type\": \"exit\"}" + // T 12

                "]}";

        String goals = "{\"goal-condition\": {\"goal\": \"exit\"}}";
        JsonArray entitiesJson = new Gson().fromJson(entities, JsonObject.class).get("entities").getAsJsonArray();
        JsonObject goalsJson = new Gson().fromJson(goals, JsonObject.class).get("goal-condition").getAsJsonObject();
        return new Dungeon(entitiesJson, goalsJson, "Standard", "", "");
    }

    // endregion

    @Test
    @Override
    public void TestBlock() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0);
        BombEntity bomb = new BombEntity(0, 1, Logic.ANY);
        entities.add(bomb);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);

        dungeon.tick(Direction.DOWN);
        assertItemInInventory("bomb-0-1-0", player, dungeon.entitiesControl);
        assertEquals(new Position(0, 1, 0), player.getPosition());

        dungeon.tick("bomb-0-1-0");
        assertEquals(new Position(0, 1, 0), dungeon.entitiesControl.getEntityById("bomb-0-1-0").getPosition(),
                "Bomb should be placed in the players new position");
        assertTrue(bomb.isArmed(), "Bomb should be active");
        assertNull(player.getInventory().getInventoryItemById(bomb.getId()), "Inventory should not contain entity " + bomb.getId());

        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 2, 0), player.getPosition(), "Player should be able to move off the bomb");
        dungeon.tick(Direction.UP);
        assertEquals(new Position(0, 2, 0), player.getPosition(),
                "Player should not be able to move back onto the bomb once it has been placed");
        assertNull(player.getInventory().getInventoryItemById(bomb.getId()), "Inventory should not contain entity " + bomb.getId());
    }

    @Test
    @Override
    public void TestUnblock() {
        // Bombs cannot be unblocked
    }

    @Test
    public void TestBombCannotBeMadeWithNotLogic() {
        assertThrows(InvalidActionException.class, () -> new BombEntity(Logic.NOT));
    }
}
