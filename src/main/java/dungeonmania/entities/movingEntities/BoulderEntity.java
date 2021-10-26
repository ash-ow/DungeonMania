package dungeonmania.entities.movingEntities;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderEntity extends Entity implements IInteractingEntity {
    public BoulderEntity() {
        this(0, 0, 0);
    }
    
    public BoulderEntity(int x, int y, int layer) {
        super(x, y, layer, "boulder");
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }


    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(this.getId(), this.getType(), this.getPosition(), false);
    }

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        Position target = this.getPosition().translateBy(direction);
        List<IEntity> targetEntities = entities.entitiesFromPosition(target);
        if ((targetEntities.size() == 0) || !EntitiesControl.entitiesUnpassable(targetEntities)) {
            this.move(direction, targetEntities.size());
            return true;
        }
        return false;
    }
}
