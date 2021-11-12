package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;


public class TreasureEntity extends CollectableEntity {
    /**
     * Treasure constructor
     */
    public TreasureEntity() {
        this(0, 0);
    }
    
    /**
     * Treasure constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public TreasureEntity(int x, int y) {
        super(x, y, EntityTypes.TREASURE);
    }

    public TreasureEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
}
