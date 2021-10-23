package dungeonmania.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntityTests {
    @Test
    public void testSimpleMove() {
        CharacterEntity player = new CharacterEntity(0, 4, "player");
        player.move(Direction.DOWN);
        assertEquals(player.getPosition(), new Position(0, 5));
        player.move(Direction.RIGHT);
        assertEquals(player.getPosition(), new Position(1, 5));
    }

    @Test
    public void testSimpleRestriction() {
        CharacterEntity player = new CharacterEntity(0, 4, "player");
        WallEntity wall = new WallEntity(0, 3, 0, "wall");
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(wall);
        Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
        assertEquals(player.getPosition(), new Position(0, 4));
        dungeon.tick(Direction.UP);
        assertEquals(player.getPosition(), new Position(0, 4));
    }
}
