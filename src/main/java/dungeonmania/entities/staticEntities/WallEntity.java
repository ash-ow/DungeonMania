package dungeonmania.entities.staticEntities;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;


public class WallEntity extends Entity implements IBlocker {

    public WallEntity() {
        this(0, 0);
    }
    
    public WallEntity(int x, int y) {
        super(x, y, EntityTypes.WALL);
    }

    public WallEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        return false;
    }
}
