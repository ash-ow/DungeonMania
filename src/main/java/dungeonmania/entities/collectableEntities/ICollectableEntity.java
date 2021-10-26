package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.dungeon.EntitiesControl;

import java.util.List;

public interface ICollectableEntity extends IInteractingEntity {
    /**
     * Adds the collectable entity into the character's inventory and removes the entity from the dungeon
     */
     
    public default void collected(CharacterEntity player, List<IEntity> entities) {
        for(int i=0; i<entities.size(); i++) {
            if (this.getId() == entities.get(i).getId()) {
                entities.remove(i);
                break;
            }
        }
        // Add collectable entity to player inventory
    }

    /**
     * Uses the collectable entity according to it's effect
     */
    void used(CharacterEntity player);
}
