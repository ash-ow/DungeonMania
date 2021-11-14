package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;


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
     */
    public InvincibilityPotionEntity(int x, int y) {
        super(x, y, EntityTypes.INVINCIBILITY_POTION);
    }

    public InvincibilityPotionEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
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
