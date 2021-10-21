package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ControllerExceptionTest {
    @Test
    public void testInvalidDungeonName() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dungeonManiaController.newGame("invalid name", "Standard"));
    } 
    @Test
    public void testInvalidMode() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dungeonManiaController.newGame("maze", "invalid"));
    }   
    @Test
    public void testSimple() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertDoesNotThrow(() -> dungeonManiaController.newGame("maze", "Standard"));
    }
}
