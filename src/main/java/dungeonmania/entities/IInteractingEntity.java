package dungeonmania.entities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Direction;

public interface IInteractingEntity extends IEntity {
    /**
     * Stub for the function which will be called in the Dungeon
     * Defines what kind of interaction the IInteractingEntity with have with a player
     * @param character
     */
	public boolean interactWithPlayer(EntitiesControl entities, Direction direction);
}
