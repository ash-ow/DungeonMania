package dungeonmania.entities.movingEntities.moveBehaviour;

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
}
