package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class DungeonManiaControllerTests {
    @Test
    public void testCreateBoulders() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        DungeonResponse d1 = dungeonManiaController.newGame("boulders", "Standard");
        dungeonManiaController.tick(null, Direction.DOWN);
    }
}
