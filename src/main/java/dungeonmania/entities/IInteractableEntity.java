package dungeonmania.entities;

import dungeonmania.entities.movingEntities.CharacterEntity;

public interface IInteractableEntity extends IEntity {
    public boolean interactWith(CharacterEntity player);
    public boolean removeAfterInteraction();
}
