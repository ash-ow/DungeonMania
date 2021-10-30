package dungeonmania.entities.movingEntities.spiderEntity;

import java.util.Arrays;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.util.Direction;

public class SpiderClockwise implements SpiderState {
    private List<Direction> movementPattern = Arrays.asList(Direction.RIGHT, Direction.DOWN, Direction.DOWN, Direction.LEFT, 
    Direction.LEFT, Direction.UP, Direction.UP, Direction.RIGHT);

    private Direction nextDirection;

    public SpiderClockwise() {
        this.nextDirection = Direction.UP;
    }

    public SpiderClockwise(Integer movementIndex) {
        nextDirection = getDirection(movementIndex);
    }


    @Override
    public Boolean moveSpider(Integer movementIndex, SpiderEntity spider, EntitiesControl entities) {
        Direction direction = nextDirection;
        List<IEntity> targetEntities = entities.getAllEntitiesFromPosition(spider.getPosition().translateBy(direction));
        if (EntitiesControl.getFirstEntityOfType(targetEntities, BoulderEntity.class) != null) {
            return false;
        }
        nextDirection = getDirection(movementIndex);
        spider.move(direction);
        return true;
    }

    private Direction getDirection(Integer movementIndex) {
        if (movementIndex < 0) {
            return movementPattern.get((movementPattern.size() + movementIndex) % 8);
        } 
        return movementPattern.get(movementIndex % 8);
    }
}
