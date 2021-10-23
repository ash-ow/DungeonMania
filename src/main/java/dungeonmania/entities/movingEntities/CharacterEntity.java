package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class CharacterEntity extends Entity implements IMovingEntity {
    public CharacterEntity() {
        super("CharacterType");
    }

    public CharacterEntity(int x, int y, String type) {
        super(x, y, type);
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
