package dungeonmania.entities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.staticEntities.SwitchEntity;
import dungeonmania.entities.staticEntities.WireEntity;

public abstract class LogicEntity extends Entity implements ITicker {

    private Logic logic;

    protected LogicEntity(int x, int y, Logic logic, EntityTypes type) {
        super(x,y,type);
        this.logic = logic;
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        List<SwitchEntity> adjacentSwitches = getAdjacentSwitches(entitiesControl);
        if (getLogicFromSwitches(adjacentSwitches)) {
            this.activate();
        } else {
            this.deactivate();
        }
    }
    
    public List<SwitchEntity> getAdjacentSwitches(EntitiesControl entitiesControl) {
        List<IEntity> adjacentEntities = entitiesControl.getAllAdjacentEntities(this.getPosition());
        List<WireEntity> adjacentWires = EntitiesControl.getEntitiesOfType(adjacentEntities, WireEntity.class);
        List<SwitchEntity> adjacentSwitches = EntitiesControl.getEntitiesOfType(adjacentEntities, SwitchEntity.class);
        for (WireEntity wire: adjacentWires) {
            adjacentSwitches.addAll(wire.followWire(this.position, entitiesControl));
        }
        return adjacentSwitches;
    }

    protected abstract void activate();
    protected abstract void deactivate();

    public boolean getLogicFromSwitches(List<SwitchEntity> switches) {
        switch (this.logic) {
            case ANY:
                return switches.stream().anyMatch(SwitchEntity::isActive);
            case AND:
                return switches.stream().allMatch(SwitchEntity::isActive);
            case OR:
                return switches.stream().anyMatch(SwitchEntity::isActive);
            case XOR:
                return switches.stream().filter(SwitchEntity::isActive).count() == 1;
            case NOT:
                return switches.stream().noneMatch(SwitchEntity::isActive);
            // case CO_AND:
            //     return switches.stream().anyMatch(SwitchEntity::isActive);
            default:
                return false;
        }
    }
}
