package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class InvisibilityPotionEntity extends CollectableEntity {
    /**
     * Invisibility potion constructor
     */
    public InvisibilityPotionEntity() {
        this(0, 0, 0);
    }
    
    /**
     * Invisibility Potion constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public InvisibilityPotionEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.INVISIBILITY_POTION);
    }

    /**
     * Gives the player invisibility and removes the potion from the players inventory
     * @param player CharacterEntity using the potion
     */
    @Override
    public void used(CharacterEntity player) {
        player.setInvisiblilityRemaining(10);
        player.removeEntityFromInventory(this);
    }
}