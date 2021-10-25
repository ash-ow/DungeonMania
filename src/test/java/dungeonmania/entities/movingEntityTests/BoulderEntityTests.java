package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderEntityTests {
    @Test
    public void testBoulderMove() {
        CharacterEntity player = new CharacterEntity(0, 0, "player");
        BoulderEntity boulder = new BoulderEntity(0, 1, 0, "boulder");
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 1));
        assertEquals(boulder.getPosition(), new Position(0, 2));
        assertEquals(boulder.getPosition().getLayer(), 0);
        dungeon.tick(Direction.RIGHT);
        assertEquals(player.getPosition(), new Position(1, 1));
        assertEquals(boulder.getPosition(), new Position(0, 2));
        assertEquals(boulder.getPosition().getLayer(), 0);
    }

    @Test
    public void testBoulderBlocked() {
        CharacterEntity player = new CharacterEntity(0, 0, "player");
        BoulderEntity boulder = new BoulderEntity(0, 1, 0, "boulder");
        WallEntity wall = new WallEntity(0, 2, 0, "wall");
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(wall);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 0));
        assertEquals(boulder.getPosition(), new Position(0, 1, 0));
    }

    @Test
    public void testBoulderOverlay() {
        CharacterEntity player = new CharacterEntity(0, 0, "player");
        BoulderEntity boulder = new BoulderEntity(0, 1, 1, "boulder");
        SwitchEntity switches = new SwitchEntity(0, 1, 0, "switch");
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
        CharacterEntity player = new CharacterEntity(0, 0, "player");
        BoulderEntity boulder = new BoulderEntity(0, 1, 0, "boulder");
        SwitchEntity switches = new SwitchEntity(0, 2, 0, "switch");
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        entities.add(switches);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        dungeon.tick(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 1));
        assertEquals(boulder.getPosition(), new Position(0, 2));
        assertEquals(boulder.getPosition().getLayer(), 1);
    }
}