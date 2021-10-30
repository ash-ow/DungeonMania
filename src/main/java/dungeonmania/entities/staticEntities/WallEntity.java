package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.util.Direction;

public class WallEntity extends Entity implements IBlocker {
    public WallEntity() {
        this(0, 0, 0);
    }
    
    public WallEntity(int x, int y, int layer) {
        super(x, y, layer, "wall");
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        // Walls are always blocking non-spider entities
        return ent instanceof SpiderEntity;
    }
}
