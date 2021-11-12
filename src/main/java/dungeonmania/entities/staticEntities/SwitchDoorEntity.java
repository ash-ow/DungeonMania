package dungeonmania.entities.staticEntities;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.Logic;
import dungeonmania.entities.LogicEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;

public class SwitchDoorEntity extends LogicEntity implements IBlocker{

    private boolean isLocked;


    public SwitchDoorEntity(int x, int y, Logic logic) {
        super(x, y, logic, EntityTypes.SWITCH_DOOR);
        this.isLocked = true;
    }

    public SwitchDoorEntity(JsonObject info) {
        this(
            info.get("x").getAsInt(),
            info.get("y").getAsInt(),
            Logic.getLogicFromJsonObject(info)
        );
    }

    @Override
    protected void activate(EntitiesControl entitiesControl) {            
        this.isLocked = false;
        this.type = EntityTypes.UNLOCKED_DOOR;
    }

    @Override
    protected void deactivate() {
        this.isLocked = true;
        this.type = EntityTypes.SWITCH_DOOR;
    }

    @Override
    public boolean isBlocking() {
        return this.isLocked;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        return !this.isLocked;
    }
}
