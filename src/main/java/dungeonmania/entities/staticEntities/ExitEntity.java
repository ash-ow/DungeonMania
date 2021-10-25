package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;

public class ExitEntity extends Entity {
    public ExitEntity() {
        this(0, 0, 0);
    }
    
    public ExitEntity(int x, int y, int layer) {
        super(x, y, layer, "exit");
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }
}
