package dungeonmania.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    private Direction(Position offset) {
        this.offset = offset;
    }

    private Direction(int x, int y) {
        this.offset = new Position(x, y);
    }

    public Position getOffset() {
        return this.offset;
    }

    public static Direction getRandomDirection(Random rand) {
        List<Direction> directionList = Direction.getAllDirections();
        Collections.shuffle(directionList, rand);
        return directionList.get(0);
    }

    public static Direction getOppositeDirection(Direction dir) {
        switch (dir) {
            case UP: return DOWN;
            case DOWN: return UP;
            case RIGHT: return LEFT;
            case LEFT: return RIGHT;
            default: return NONE;
        }
    }
    
}
