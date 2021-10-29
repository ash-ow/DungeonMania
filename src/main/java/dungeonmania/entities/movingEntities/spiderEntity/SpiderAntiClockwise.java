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

    public SpiderAntiClockwise(Integer movementIndex) {
        nextDirection = getDirection(movementIndex);
    }

    @Override
    public Boolean moveSpider(Integer movementIndex, SpiderEntity spider, EntitiesControl entities) {
        Direction direction = nextDirection;
        List<IEntity> targetEntities = entities.getAllEntitiesFromPosition(spider.getPosition().translateBy(direction));
        if (EntitiesControl.entitiesContainsType(targetEntities, BoulderEntity.class) != null) {
            return false;
        }
        nextDirection = getDirection(movementIndex);
        spider.setPosition(
            spider.getPosition().translateBy(direction)
        );

        return true;
    }

    private Direction getDirection(Integer movementIndex) {
        if (movementIndex < 0) {
            return movementReversePattern.get((movementReversePattern.size() + movementIndex) % 8);
        } 
        return movementReversePattern.get(movementIndex % 8);
    }
}
