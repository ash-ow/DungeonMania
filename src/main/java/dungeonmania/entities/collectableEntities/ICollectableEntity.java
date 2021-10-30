package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Direction;

public interface ICollectableEntity extends IContactingEntity {
    /**
     * Adds the collectable entity into the character's inventory and removes the entity from the dungeon
     */
    @Override    
    public default boolean contactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        player.addEntityToInventory(this);
        entities.removeEntity(this);
        return true;
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
