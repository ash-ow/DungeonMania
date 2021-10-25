package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;

public class WallEntity extends Entity {
    public WallEntity() {
        this(0, 0, 0);
    }
    
    public WallEntity(int x, int y, int layer) {
        super(x, y, layer, "wall");
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }
}
