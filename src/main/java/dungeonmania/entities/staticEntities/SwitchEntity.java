package dungeonmania.entities.staticEntities;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.BoulderEntity;


public class SwitchEntity extends Entity implements ITicker {
    private boolean active = false;
    private boolean isActiveThisRound = false;

    public SwitchEntity() {
        this(0, 0);
    }
    
    public SwitchEntity(int x, int y) {
        super(x, y, EntityTypes.SWITCH);
    }

    public SwitchEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        List<BoulderEntity> boulders = entitiesControl.getEntitiesOfType(BoulderEntity.class);
        setIsActiveThisRound(boulders);
        this.active = isSwitchCovered(boulders);
    }

    private void setIsActiveThisRound(List<BoulderEntity> boulders) {
        // If it was activated last round, then set this property to false
        if (this.isActiveThisRound) {
            this.isActiveThisRound = false;
        } else
        // If the switch is not active and the switch is covered, then the boulder must have been pushed in this round
        if (!this.active && isSwitchCovered(boulders)) {
            this.isActiveThisRound = true;
        }
    }

    public boolean isSwitchCovered(List<BoulderEntity> boulders) {
        return boulders.stream().anyMatch(b -> b.getPosition().equals(this.position));
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isActiveThisRound() {
        return this.isActiveThisRound;
    }
}
