package dungeonmania.entities.movingEntities.moveBehaviour;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface IMovingBehaviour {
    public Direction getBehaviourDirection(EntitiesControl entitiesControl, CharacterEntity player, Position position);
}
