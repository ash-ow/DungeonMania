package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;


public class ArrowsEntity extends CollectableEntity {
    /**
     * Arrows constructor
     */
    public ArrowsEntity() {
        this(0, 0);
    }
    
    /**
     * Arrows constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public ArrowsEntity(int x, int y) {
        super(x, y, EntityTypes.ARROW);
    }

    public ArrowsEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
}
