package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;

public class SwitchEntity extends Entity {
    public SwitchEntity() {
        this(0, 0, 0);
    }
    
    public SwitchEntity(int x, int y, int layer) {
        super(x, y, layer, "switch");
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }
}
