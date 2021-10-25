package dungeonmania.entities.movingEntityTests;

import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface IMovingEntityTest {
    public void TestMove();

    public default void assertPositionEquals(Position position, int x, int y) {
        assertEquals(new Position(x, y, 0), position);
    }
}