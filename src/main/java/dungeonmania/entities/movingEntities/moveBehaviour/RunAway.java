package dungeonmania.entities.movingEntities.moveBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RunAway extends FollowPlayer{
    @Override
    public Direction getBehaviourDirection(EntitiesControl entitiesControl, CharacterEntity player, Position position) {
        List<Direction> usefulDirections = getUsefuDirections(player, position).stream().map(dir -> Direction.getOppositeDirection(dir)).collect(Collectors.toList());
        return moveToUsefulUnblocked(usefulDirections, entitiesControl, position);
    }

    public List<Direction> getUsefuDirections(CharacterEntity player, Position position) {
        Position diff = Position.calculatePositionBetween(position, player.getPosition());
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
    
    public Direction moveToUsefulUnblocked(List<Direction> usefulDirections, EntitiesControl entitiesControl, Position position) {
        for (Direction d : usefulDirections) {
            if (!targetPositionIsBlocked(d, entitiesControl, position)) {
                return d;
            }
        }
        return Direction.NONE;
    }

    private boolean targetPositionIsBlocked(Direction d, EntitiesControl entitiesControl, Position position) {
        Position target = position.translateBy(d);
        return EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(target));
    }
}
