package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.JsonControl;

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
     * @param layer layer on the map 
     */
    public HealthPotionEntity(int x, int y) {
        super(x, y, EntityTypes.HEALTH_POTION);
    }

    public HealthPotionEntity(JsonControl info) {
        this(info.getPosition().getX(), info.getPosition().getY());
    }

    /**
     * Gives the player full health and removes the potion from the players inventory
     * @param player CharacterEntity using the potion
     */
    @Override
    public void used(CharacterEntity player) {
        player.setHealth(100);
        player.removeEntityFromInventory(this);
    }   
}