package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class CharacterEntity extends Entity implements IMovingEntity {
    public CharacterEntity() {
        super("CharacterType");
    }

    public CharacterEntity(int x, int y, String type) {
        super(
            "character-" + x + "-" + y, // id
            x, y, type
        );
    }

    public EntityResponse getInfo() {
        return new EntityResponse(this.getId(), this.getType(), this.getPosition(), false);
    }

    @Override
    public boolean passable() {
        return false;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
