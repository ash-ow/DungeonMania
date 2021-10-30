package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;

public class ZombieToastSpawnerEntity extends Entity implements IBlocker {
    // TODO make this IBlocker

    public ZombieToastSpawnerEntity() {
        this(0, 0, 0);
    }
    
    public ZombieToastSpawnerEntity(int x, int y, int layer) {
        super(x, y, layer, "zombie_toast_spawner");
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        // cannot unblock zombie spawners
        return false;
    }
}
