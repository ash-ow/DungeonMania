package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.List;

public interface ICollectableEntity extends IInteractingEntity {
    /**
     * Adds the collectable entity into the character's inventory and removes the entity from the dungeon
     */
    @Override    
    public default boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        player.addEntityToInventory(this);
        entities.removeEntity(this);
        return true;
    }

    /**
     * Decrements the amount of times a collectable entity can still be used and removes it from inventory
     * if no more uses
     */
    void used(CharacterEntity player);

    /**
     * Adds the collectable item to the player inventory
     * @param player
     */
    void collect(CharacterEntity player);
}
