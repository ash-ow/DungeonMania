package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.DungeonEntityJsonObject;

public class InvincibilityPotionEntity extends CollectableEntity {
    /**
     * Invincibility potion constructor
     */
    public InvincibilityPotionEntity() {
        this(0, 0);
    }
    
    /**
     * Invincibility Potion constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public InvincibilityPotionEntity(int x, int y) {
        super(x, y, EntityTypes.INVINCIBILITY_POTION);
    }

    public InvincibilityPotionEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    /**
     * Gives the player invincibiility and removes the potion from the players inventory
     * @param player CharacterEntity using the potion
     */
    @Override
    public void used(CharacterEntity player) {
       player.setInvincibilityRemaining(10);
       player.removeEntityFromInventory(this);
    } 
}
