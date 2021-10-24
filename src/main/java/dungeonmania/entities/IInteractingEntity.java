package dungeonmania.entities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface IInteractingEntity extends IEntity {
    /**
     * Stub for the function which will be called in the Dungeon
     * Defines what kind of interaction the IInteractingEntity with have with a player
     * @param character
     */
	public boolean interactWithPlayer(EntitiesControl entities, Direction direction);
}
