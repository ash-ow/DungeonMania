package dungeonmania.entities;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.exceptions.InvalidActionException;

public interface IInteractingEntity {
    public void interactWith(CharacterEntity player) throws InvalidActionException;
}
