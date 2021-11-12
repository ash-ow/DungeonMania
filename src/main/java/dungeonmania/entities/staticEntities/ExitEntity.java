package dungeonmania.entities.staticEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;


public class ExitEntity extends Entity {
    public ExitEntity() {
        this(0, 0);
    }
    
    public ExitEntity(int x, int y) {
        super(x, y, EntityTypes.EXIT);
    }

    public ExitEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
}
