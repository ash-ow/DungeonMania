package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ControllerExceptionTest {
    @Test
    public void testInvalidDungeonName() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
<<<<<<< HEAD
        assertThrows(IllegalArgumentException.class, () -> dungeonManiaController.newGame("invalid name", "Standard"));
=======
        assertThrows(IllegalArgumentException.class, () -> dungeonManiaController.newGame("invalid name", "standard"));
>>>>>>> dbf8f62 (testing exceptions for new game)
    } 
    @Test
    public void testInvalidMode() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dungeonManiaController.newGame("maze", "invalid"));
    }   
    @Test
    public void testSimple() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
<<<<<<< HEAD
        assertDoesNotThrow(() -> dungeonManiaController.newGame("maze", "Standard"));
=======
        assertDoesNotThrow(() -> dungeonManiaController.newGame("maze", "standard"));
>>>>>>> dbf8f62 (testing exceptions for new game)
    }
}
