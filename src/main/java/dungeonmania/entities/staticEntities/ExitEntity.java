package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public class ExitEntity extends Entity {
    public ExitEntity() {
        this(0, 0, 0);
    }
    
    public ExitEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.EXIT);
    }
}
