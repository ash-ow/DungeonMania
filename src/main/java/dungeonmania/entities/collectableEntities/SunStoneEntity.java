package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;


public class SunStoneEntity extends CollectableEntity {
    public SunStoneEntity() {
        this(0,0);
    }

    public SunStoneEntity(int x, int y) {
        super(x, y, EntityTypes.SUN_STONE);
    }

    public SunStoneEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
}
