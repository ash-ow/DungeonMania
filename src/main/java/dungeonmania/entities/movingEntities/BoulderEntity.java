package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntities.StaticEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class BoulderEntity extends Entity implements IMovingEntity {

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
}
