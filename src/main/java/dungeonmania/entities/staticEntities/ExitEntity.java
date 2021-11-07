package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonObject;

public class ExitEntity extends Entity {
    public ExitEntity() {
        this(0, 0);
    }
    
    public ExitEntity(int x, int y) {
        super(x, y, EntityTypes.EXIT);
    }

    public ExitEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }
}
