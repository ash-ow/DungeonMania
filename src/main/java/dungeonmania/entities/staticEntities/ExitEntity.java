package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.JsonControl;

public class ExitEntity extends Entity {
    public ExitEntity() {
        this(0, 0);
    }
    
    public ExitEntity(int x, int y) {
        super(x, y, EntityTypes.EXIT);
    }

    public ExitEntity(JsonControl info) {
        this(info.getPosition().getX(), info.getPosition().getY());
    }
}
