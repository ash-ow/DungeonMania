package dungeonmania.entities.staticEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.BoulderEntity;

public class SwitchEntity extends Entity implements ITicker {
    private boolean active = false;

    public SwitchEntity() {
        this(0, 0, 0);
    }
    
    public SwitchEntity(int x, int y, int layer) {
        super(x, y, layer, "switch");
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        // TODO switches check if there is a boulder on their location and set isActive = true
        List<BoulderEntity> boulders = entitiesControl.getEntitiesOfType(BoulderEntity.class);
        this.active = isSwitchCovered(boulders);
    }

    public boolean isSwitchCovered(List<BoulderEntity> boulders) {
        return boulders.stream().anyMatch(b -> b.getPosition().equals(this.position));
    }

    public boolean isActive() {
        // TODO true if there is a boulder on the switch
        return active;
    }
}
