package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.DungeonEntityJsonParser;

public class WallEntity extends Entity implements IBlocker {

    public WallEntity() {
        this(0, 0);
    }
    
    public WallEntity(int x, int y) {
        super(x, y, EntityTypes.WALL);
    }

    public WallEntity(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
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
