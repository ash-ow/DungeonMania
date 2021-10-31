package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.dungeon.EntitiesControl;

public interface CollectableEntity extends Entity  IContactingEntity {
    /**
     * Adds the collectable entity into the character's inventory and removes the entity from the dungeon
     */
    @Override    
    public default void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        player.addEntityToInventory(this);
        entities.removeEntity(this);
    }

    /**
     * Deletes an item from the players inventory after it has been used.
     */
    public default void used(CharacterEntity player) {
        if (this.getDurability() <= 0) {
            player.removeEntityFromInventory(this);
        }
        this.decrementDurability();
    }

    public void decrementDurability();
    public int getDurability();

    /**
     * @return True if the item is supposed to be placed on the player location after being used
     */
    boolean isPlacedAfterUsing();
}
