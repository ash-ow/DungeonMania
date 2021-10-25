package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;

public class DoorEntity extends Entity {
    public DoorEntity() {
        this(0, 0, 0);
    }
    
    public DoorEntity(int x, int y, int layer) {
        super(x, y, layer, "door");
    }
    
    @Override
    public boolean isPassable() {
        // TODO implement locking
        return false;
    }
}
