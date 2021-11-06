package dungeonmania.entities.staticEntities;

import java.util.HashMap;
import java.util.Map;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;

public class SwampEntity extends Entity implements IBlocker {
    public SwampEntity() {
        this(0, 0, 0);
    }
    
    public SwampEntity(int x, int y, int layer) {
        super(x, y, layer, "swamp");
    }

    Map<Entity, Integer> entitiesTryingToPass = new HashMap<Entity, Integer>();

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        return false;
    }
}
