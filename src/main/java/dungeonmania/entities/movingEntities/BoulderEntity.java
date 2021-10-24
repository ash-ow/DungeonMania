package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;

public class BoulderEntity extends Entity {
    public BoulderEntity() {
        this(0, 0, 0);
    }
    
    public BoulderEntity(int x, int y, int layer) {
        super(x, y, layer, "boulder");
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }
}
