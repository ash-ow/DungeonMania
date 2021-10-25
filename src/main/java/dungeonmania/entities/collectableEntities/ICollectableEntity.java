package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IEntity;
import dungeonmania.util.Position;

public interface ICollectableEntity extends IEntity {
    /**
     * Adds the collectable entity into the character's inventory and removes the entity from the dungeon
     */
    void collected();

    /**
     * Uses the collectable entity according to it's effect
     */
    void used();
}
