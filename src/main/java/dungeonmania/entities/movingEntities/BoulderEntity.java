package dungeonmania.entities.movingEntities;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderEntity extends Entity implements IBlocker {
    public BoulderEntity() {
        this(0, 0, 0);
    }
    
    public BoulderEntity(int x, int y, int layer) {
        super(x, y, layer, "boulder");
    }

    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(this.getId(), this.getType(), this.getPosition(), false);
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        Position target = this.getPosition().translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) ) {
            position = position.translateBy(direction).asLayer(targetEntities.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public void setIsBlocking(boolean isBlocking) {
        // The boulder entity is always blocking
    }
}
