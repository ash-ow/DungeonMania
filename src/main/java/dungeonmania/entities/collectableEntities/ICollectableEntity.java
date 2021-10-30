package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.dungeon.EntitiesControl;

public interface ICollectableEntity extends IInteractingEntity {
    /**
     * Adds the collectable entity into the character's inventory and removes the entity from the dungeon
     */
    @Override    
    public default void interactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        player.addEntityToInventory(this);
        entities.removeEntity(this);
    }

    /**
     * Deletes an item from the players inventory after it has been used.
     */
    public default void used(CharacterEntity player) {
        player.removeEntityFromInventory(this);
    }

    /**
     * @return True if the item is supposed to be placed on the player location after being used
     */
    boolean isPlacedAfterUsing();
}
