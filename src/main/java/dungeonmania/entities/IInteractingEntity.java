package dungeonmania.entities;

import dungeonmania.entities.movingEntities.CharacterEntity;

public interface IInteractingEntity extends IEntity {
    /**
     * Stub for the function which will be called in the Dungeon
     * Defines what kind of interaction the IInteractingEntity with have with a player
     * @param character
     */
	public void interactWithPlayer(CharacterEntity character);
}
