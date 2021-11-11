package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlockerTest;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WallEntity;

public class BoulderEntityTests implements IMovingEntityTest, IEntityTests, IBlockerTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        BoulderEntity boulder = new BoulderEntity(0, 0, 0);
        assertEntityResponseInfoEquals(
            boulder,
            "boulder-0-0-0",
            EntityTypes.BOULDER,
            new Position(0,0),
            false
        );
    }

    @Override
    @Test
    public void TestMove() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
    
        entities.add(boulder);
        Dungeon dungeon = new Dungeon(entities, "standard", player);
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
    @Override
    public void TestBlock() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        WallEntity wall = new WallEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(wall);
        Dungeon dungeon = new Dungeon(entities, "standard", player);
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
        Dungeon dungeon = new Dungeon(entities, "standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 1), player.getPosition(), "Player should move boulder");
        assertEquals(new Position(0, 2), boulder.getPosition(), "Player should move boulder");
        assertEquals(0, boulder.getPosition().getLayer());
    }

    @Test
    public void testBoulderPushToSwitch() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        SwitchEntity switches = new SwitchEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switches);
        Dungeon dungeon = new Dungeon(entities, "standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 1));
        assertEquals(boulder.getPosition(), new Position(0, 2));
        assertEquals(1, boulder.getPosition().getLayer());
        assertTrue(switches.isActive());
    }

    @Override
    @Test
    public void TestUnblock() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BoulderEntity boulder = new BoulderEntity(0, 1, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(entities, "standard", player, j);
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 2, 0), boulder.getPosition());
    }
}
