package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface IAutoMovingEntity extends IMovingEntity{
    public void move(EntitiesControl entitiesControl, CharacterEntity player);

    /**
     * Moves an autoMovingEntity
     * @param entitiesControl           list of all entities
     * @param player                    player which the entity is moving away from
     */
    public default void runAway(EntitiesControl entitiesControl, CharacterEntity player) {
        List<Direction> usefulDirections = getUsefuDirections(player).stream().map(dir -> Direction.getOppositeDirection(dir)).collect(Collectors.toList());
        moveToUsefulUnblocked(usefulDirections, entitiesControl);
    }

    /**
     * Returns the list of directions for the entity to move
     * @param player allows calculation for useful directions
     */
    public default List<Direction> getUsefuDirections(CharacterEntity player) {
        Position diff = Position.calculatePositionBetween(this.getPosition(), player.getPosition());
        List<Direction> usefulDirections = new ArrayList<>();
        if (diff.getX() < 0) {
            usefulDirections.add(Direction.LEFT);
        } else if (diff.getX() > 0) {
            usefulDirections.add(Direction.RIGHT);
        } 
        if (diff.getY() < 0) {
            usefulDirections.add(Direction.UP);
        } else if (diff.getY() > 0) {
            usefulDirections.add(Direction.DOWN);
        }
        if (usefulDirections.isEmpty()) {
            usefulDirections.add(Direction.NONE);
        }
        return usefulDirections;    
    }
    
    /**
     * Moves an autoMovingEntity to an unblocked location
     * @param usefulDirection           list of useful directions
     * @param entitiesControl           list of all entities
     */
    public default void moveToUsefulUnblocked(List<Direction> usefulDirections, EntitiesControl entitiesControl) {
        for (Direction d : usefulDirections) {
            if (!targetPositionIsBlocked(d, entitiesControl)) {
                this.move(d);
                break;
            }
        }
    }

    /**
     * Checks if a position is blocked
     * @param d                         direction to be moved
     * @param entitiesControl           list of all entities
     */
    private boolean targetPositionIsBlocked(Direction d, EntitiesControl entitiesControl) {
        Position target = this.getPosition().translateBy(d);
        return EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(target));
    }
}
