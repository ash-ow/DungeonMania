package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.ITicker;

public class SwitchEntity extends Entity implements ITicker {
    public SwitchEntity() {
        this(0, 0, 0);
    }
    
    public SwitchEntity(int x, int y, int layer) {
        super(x, y, layer, "switch");
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        // TODO switches check if there is a boulder on their location and set isActive = true
    }

    public boolean isActive() {
        // TODO true if there is a boulder on the switch
        return true;
    }
}
