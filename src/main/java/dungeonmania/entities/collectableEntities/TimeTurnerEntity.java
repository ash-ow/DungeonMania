package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;


public class TimeTurnerEntity extends CollectableEntity{
    public TimeTurnerEntity() {
        this(0, 0);
    }

    public TimeTurnerEntity(int x, int y) {
        super(x, y, EntityTypes.TIME_TURNER);
    }

    public TimeTurnerEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
}
