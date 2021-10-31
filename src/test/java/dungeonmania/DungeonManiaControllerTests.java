package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import dungeonmania.exceptions.InvalidActionException;
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
    public void testIllegalArgumentExceptionFail() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.newGame("test", "easy"), "gameMode is not a valid game mode");
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.newGame("fly", "Hard"), "dungeonName is not a dungeon that exists");

        dungeonManiaController.newGame("testExeptions", "Hard");
        dungeonManiaController.saveGame("test");

        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.loadGame("Some Random ID"), "id is not a valid game id");
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.build("Death Star"), "buildable is not one of bow, shield");
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.interact("Execute Order 66"), "entityId is not a valid entity ID");
    }

    @Test
    public void testIllegalArgumentExceptionFail2() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();

        dungeonManiaController.newGame("testExeptions", "Hard");
        dungeonManiaController.tick(null, Direction.DOWN);

        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.tick("1", Direction.LEFT));
    }

    @Test
    public void testIllegalArgumentExceptionPass() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertDoesNotThrow(() -> dungeonManiaController.newGame("test", "Hard"));
    }

    @Test
    public void testInvalidActionExceptionFail() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        dungeonManiaController.newGame("testExeptions", "Standard");
        dungeonManiaController.tick(null, Direction.DOWN);
        assertThrows(InvalidActionException.class,
        () -> dungeonManiaController.tick("health_potion", Direction.DOWN));
        assertThrows(InvalidActionException.class,
        () -> dungeonManiaController.build("shield"));
    }

    @Test
    public void testInvalidActionExceptionPass() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        dungeonManiaController.newGame("testExeptions", "Standard");
        dungeonManiaController.tick(null, Direction.DOWN);
        assertDoesNotThrow(() -> dungeonManiaController.tick("0", Direction.DOWN));
        assertDoesNotThrow(() -> dungeonManiaController.build("bow"));
    }
}
