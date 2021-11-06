package dungeonmania.entities.movingEntities;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.DungeonEntityJsonParser;
import dungeonmania.util.Position;

public class BoulderEntity extends Entity implements IBlocker {
    /**
     * Boulder constructor
     */
    public BoulderEntity() {
        this(0, 0);
    }
    
    /**
     * Boulder constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public BoulderEntity(int x, int y) {
        super(x, y, EntityTypes.BOULDER);
    }

    public BoulderEntity(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }

    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(this.getId(), this.getType(), this.getPosition(), false);
    }

    /**
     * If a boulder can be moved into the next direction, it will be moved
     * @param ent               the entity trying to move the entity
     * @param direction         next direction
     * @param entitiesControl   list of entities
     */
    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        Position target = this.getPosition().translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) ) {
            this.position = position.translateBy(direction).asLayer(targetEntities.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlocking() {
        return true;
    }
}
