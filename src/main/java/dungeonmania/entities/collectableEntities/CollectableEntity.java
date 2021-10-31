package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.dungeon.EntitiesControl;

public abstract class CollectableEntity extends Entity implements IContactingEntity {
    protected int durability;

    protected CollectableEntity(int x, int y, int layer, EntityTypes type) {
        super(x, y, layer, type);
    }

    /**
     * Adds the collectable entity into the character's inventory, 
     * removes the entity from the dungeon
     */
    @Override    
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        player.addEntityToInventory(this);
        entities.removeEntity(this);
    }

    /**
     * Deletes an item from the players inventory after it has been used a max number of times
     */
    public void used(CharacterEntity player) {
        if (this.getDurability() <= 0) {
            player.removeEntityFromInventory(this);
        }
        this.durability--;
    }

    public int getDurability() {
        return this.durability;
    }

    /**
     * @return True if the item is supposed to be placed on the player location after being used
     */
    public abstract boolean isPlacedAfterUsing();
}
