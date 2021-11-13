package dungeonmania.entities.staticEntities;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.Logic;
import dungeonmania.entities.LogicEntity;


public class LightBulbEntity extends LogicEntity {
    public LightBulbEntity(Logic logic) {
        this(0, 0, logic);
    }
    
    public LightBulbEntity(int x, int y, Logic logic) {
        super(x, y, logic, EntityTypes.LIGHT_BULB_OFF);
    }

    public LightBulbEntity(JsonObject info) {
        this(
            info.get("x").getAsInt(),
            info.get("y").getAsInt(),
            Logic.getLogicFromJsonObject(info)
        );
    }

    @Override
    protected void activate(EntitiesControl entitiesControl) {
        this.type = EntityTypes.LIGHT_BULB_ON;
    }

    @Override
    protected void deactivate() {
        this.type = EntityTypes.LIGHT_BULB_OFF;
    }

    public boolean isActive() {
        return this.type == EntityTypes.LIGHT_BULB_ON;
    }
}
