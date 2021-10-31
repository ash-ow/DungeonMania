package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface IAutoMovingEntity extends IMovingEntity{
    public void move(EntitiesControl entitiesControl, CharacterEntity player);

    public default void runAway(EntitiesControl entitiesControl, CharacterEntity player) {
        List<Direction> usefulDirections = getRunAwayDirections(player);
        moveToUsefulUnblocked(usefulDirections, entitiesControl);
    }

    private List<Direction> getRunAwayDirections(CharacterEntity player) {
        Position diff = Position.calculatePositionBetween(this.getPosition(), player.getPosition());
        List<Direction> usefulDirections = new ArrayList<>();
        if (diff.getX() < 0) {
            usefulDirections.add(Direction.RIGHT);
        } else if (diff.getX() > 0) {
            usefulDirections.add(Direction.LEFT);
        } 
        if (diff.getY() < 0) {
            usefulDirections.add(Direction.DOWN);
        } else if (diff.getY() > 0) {
            usefulDirections.add(Direction.UP);
        }
        if (usefulDirections.isEmpty()) {
            usefulDirections.add(Direction.NONE);
        }
        return usefulDirections;    
    }

    public default void moveToUsefulUnblocked(List<Direction> usefulDirections, EntitiesControl entitiesControl) {
        for (Direction d : usefulDirections) {
            if (!targetPositionIsBlocked(d, entitiesControl)) {
                this.move(d);
                break;
            }
        }
    }

    private boolean targetPositionIsBlocked(Direction d, EntitiesControl entitiesControl) {
        Position target = this.getPosition().translateBy(d);
        return EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(target));
    }
}
