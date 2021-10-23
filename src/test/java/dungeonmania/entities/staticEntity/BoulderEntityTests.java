package dungeonmania.entities.staticEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.CharacterEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.staticEntities.BoulderEntity;
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
        assertEquals(boulder.getPosition(), new Position(0, 2, 0));
    }
}
