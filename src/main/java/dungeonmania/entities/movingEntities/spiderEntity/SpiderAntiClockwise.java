package dungeonmania.entities.movingEntities.spiderEntity;

import java.util.Arrays;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.util.Direction;

public class SpiderAntiClockwise implements SpiderState {
    private List<Direction> movementReversePattern = Arrays.asList(Direction.LEFT, Direction.DOWN, Direction.DOWN, Direction.RIGHT, 
    Direction.RIGHT, Direction.UP, Direction.UP, Direction.LEFT);

    private Direction nextDirection;

    /**
     * Setter for spider direction
     * @param movementIndex gives the spider its next direction
     */
    public SpiderAntiClockwise(Integer movementIndex) {
        nextDirection = getDirection(movementIndex);
    }

    /**
     * Moves the spider
     * @param movementIndex gives the spider its next direction
     * @param spider        the spider which is moving
     * @param entities      the list of entities which the spider may interact with
     * @return              Whether the spider has moved
     */
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
            return movementReversePattern.get((movementReversePattern.size() + movementIndex) % 8);
        } 
        return movementReversePattern.get(movementIndex % 8);
    }
}
