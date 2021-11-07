package dungeonmania.entities.staticEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;
import dungeonmania.util.DungeonEntityJsonObject;

public class LightBulbEntity extends Entity implements ITicker {
    public LightBulbEntity() {
        this(0, 0);
    }
    
    public LightBulbEntity(int x, int y) {
        super(x, y, EntityTypes.LIGHT_BULB_OFF);
    }

    public LightBulbEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        List<IEntity> adjacentEntities = entitiesControl.getAllAdjacentEntities(this.getPosition());
        if (containsActiveSwitch(adjacentEntities)) {
            this.type = EntityTypes.LIGHT_BULB_ON;
        } else {
            this.type = EntityTypes.LIGHT_BULB_OFF;
        }
    }

    public boolean isActive() {
        return this.type == EntityTypes.LIGHT_BULB_ON;
    }
}
