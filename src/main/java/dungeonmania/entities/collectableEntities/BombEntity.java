package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;

public class BombEntity extends Entity implements ICollectableEntity {
    public BombEntity() {
        this(0, 0, 0);
    }
    
    public BombEntity(int x, int y, int layer) {
        super(x, y, layer, "bomb");
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }
}
