package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;


public class HealthPotionEntity extends CollectableEntity {
    /**
     * Health potion constructor
     */
    public HealthPotionEntity() {
        this(0, 0);
    }
    
    /**
     * Health Potion constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public HealthPotionEntity(int x, int y) {
        super(x, y, EntityTypes.HEALTH_POTION);
    }

    public HealthPotionEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    /**
     * Gives the player full health and removes the potion from the players inventory
     * @param player CharacterEntity using the potion
     */
    @Override
    public void used(CharacterEntity player) {
        player.setHealth(100);
        player.getInventoryItems().remove(this);
    }   
}