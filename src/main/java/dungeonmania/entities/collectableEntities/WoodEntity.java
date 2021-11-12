package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;


public class WoodEntity extends CollectableEntity {
    /**
     * Wood constructor
     */
    public WoodEntity() {
        this(0, 0);
    }

    /**
     * Wood constructor
     */
    public WoodEntity(int x, int y) {
        super(x, y, EntityTypes.WOOD);
    }

    public WoodEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
}
