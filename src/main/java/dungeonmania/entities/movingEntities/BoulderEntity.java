package dungeonmania.entities.movingEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderEntity extends Entity implements IMovingEntity, IInteractingEntity {

    public BoulderEntity() {
        super("boulder");
    }

    public BoulderEntity(int x, int y, int layer, String type) {
        super(
            "boulder-" + x + "-" + y, // id
            x, y, type
        );
    }

    @Override
    public boolean passable() {
        return false;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
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
            player.move(direction);
            return true;
        }
        return false;
    }


}
