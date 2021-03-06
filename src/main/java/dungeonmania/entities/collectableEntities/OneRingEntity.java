package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;


public class OneRingEntity extends CollectableEntity {
    private final float dropChance = 0.1f;

    /**
     * One Ring constructor
     */
    public OneRingEntity() {
        this(0, 0);
    }

    /**
     * One Ring constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public OneRingEntity(int x, int y) {
        super(x, y, EntityTypes.ONE_RING);
    }

    public OneRingEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    public float getDropChance() {
        return dropChance;
    }

    /**
     * Gives the player full health and removes the one ring from the players inventory
     * @param player CharacterEntity using the one ring
     */
    @Override
    public void used(CharacterEntity player) {
        player.setHealth(100);
        player.getInventoryItems().remove(this);
    }
}