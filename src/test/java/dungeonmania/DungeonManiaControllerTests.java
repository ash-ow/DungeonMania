package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.ExitEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DungeonManiaControllerTests {

    @Test
    public void testNewGameWrongMode() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.newGame("test", "easy"), "gameMode is not a valid game mode");
    }

    @Test
    public void testNewGameWrongName() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.newGame("fly", "Hard"), "dungeonName is not a dungeon that exists");
    }

    @Test
    public void testLoadGameWrongId() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();

        dungeonManiaController.newGame("testExeptions", "Hard");
        dungeonManiaController.saveGame("test");

        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.loadGame("Some Random ID"), "id is not a valid game id");
    }

    @Test
    public void testBuildWrongId() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();

        dungeonManiaController.newGame("testExeptions", "Hard");
        dungeonManiaController.saveGame("test");

        assertThrows(IllegalArgumentException.class, 
        () -> dungeonManiaController.build("Death Star"), "buildable is not one of bow, shield");
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
        assertDoesNotThrow(() -> dungeonManiaController.newGame("maze", "Hard"));
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

    @Test
    public void testDungeonGeneratorBoundary() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        dungeonManiaController.generateDungeon(5, 5, 45, 39, "Standard");
        List<WallEntity> walls =  dungeonManiaController.dungeon.entitiesControl.getAllEntitiesOfType(WallEntity.class);
        List<Position> wallPositions = new ArrayList<>();
        for (WallEntity wall : walls) {
            wallPositions.add(wall.getPosition());
        }
        for(int i = 0; i < 49; i++) {
            Position position = new Position(i, 0);
            assertFalse(!wallPositions.contains(position));
            position.translateBy(0, i);
            assertFalse(!wallPositions.contains(position));
            position.translateBy(i, 49);
            assertFalse(!wallPositions.contains(position));
            position.translateBy(49, i);
            assertFalse(!wallPositions.contains(position));
        }
    }

    @Test
    public void testDungeonGeneratorEntities() {
        DungeonManiaController dungeonManiaController = new DungeonManiaController();
        dungeonManiaController.generateDungeon(5, 5, 45, 39, "Standard");
        List<ExitEntity> exit =  dungeonManiaController.dungeon.entitiesControl.getAllEntitiesOfType(ExitEntity.class);

        assertNotNull(dungeonManiaController.dungeon.getPlayer());
        assertEquals(1, exit.size());
    }
}
