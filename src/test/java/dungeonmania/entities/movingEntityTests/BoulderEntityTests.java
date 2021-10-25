package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WallEntity;

public class BoulderEntityTests implements IMovingEntityTest, IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        BoulderEntity boulder = new BoulderEntity(0, 0, 0);
        assertEntityResponseInfoEquals(
            boulder,
            "boulder-0-0-0",
            "boulder",
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestPlayerMovesBoulder() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
    
        entities.add(boulder);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);

        assertEquals(new Position(0, 1), player.getPosition());
        assertEquals(new Position(0, 2), boulder.getPosition());
        assertEquals(0, boulder.getPosition().getLayer());
        
        dungeon.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), player.getPosition());
        assertEquals(new Position(0, 2), boulder.getPosition());
        assertEquals(0, boulder.getPosition().getLayer());
    }

    @Test
    public void TestBoulderBlockedByWall() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        WallEntity wall = new WallEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(wall);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 0));
        assertEquals(boulder.getPosition(), new Position(0, 1, 0));
    }

    @Test
    public void testBoulderOverlayOnSwitch() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 1);
        SwitchEntity switches = new SwitchEntity(0, 1, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switches);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 1));
        assertEquals(boulder.getPosition(), new Position(0, 2));
        assertEquals(boulder.getPosition().getLayer(), 0);
    }

    @Test
    public void testBoulderPushToSwitch() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        SwitchEntity switches = new SwitchEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switches);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 1));
        assertEquals(boulder.getPosition(), new Position(0, 2));
        assertEquals(boulder.getPosition().getLayer(), 1);
    }

    @Override
    @Test
    public void TestMove() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player, j);
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 2, 0), boulder.getPosition());
    }
}
