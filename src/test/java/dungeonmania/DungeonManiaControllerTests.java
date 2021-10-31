package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class DungeonManiaControllerTests {
    @Test
    public void testCreateBoulders() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        DungeonResponse d1 = dungeonManiaController.newGame("boulders", "Standard");
        dungeonManiaController.tick(null, Direction.DOWN);
    }

    @Test
    public void testCreateAdvance() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        DungeonResponse d1 = dungeonManiaController.newGame("advanced", "Standard");
        dungeonManiaController.tick(null, Direction.DOWN);
    }

    @Test
    public void testIllegalArgumentException() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.newGame("test", "easy"));
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.newGame("fly", "Hard"));
    }
}
