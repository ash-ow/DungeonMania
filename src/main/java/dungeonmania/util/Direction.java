package dungeonmania.util;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0)
    ;

    private final Position offset;
    public static List<Direction> getAllDirections() {
        return Arrays.asList(
            UP, DOWN, LEFT, RIGHT
        );
    }
    
    public static Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case UP: return DOWN;
            case DOWN: return UP;
            case RIGHT: return LEFT;
            case LEFT: return RIGHT;
            default: return NONE;
        }
    }

    private Direction(Position offset) {
        this.offset = offset;
    }

    private Direction(int x, int y) {
        this.offset = new Position(x, y);
    }

    public Position getOffset() {
        return this.offset;
    }
}
