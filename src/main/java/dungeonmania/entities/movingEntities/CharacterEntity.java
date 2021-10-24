package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    float health = 100;
    public CharacterEntity() {
        super("CharacterType");
    }

    public CharacterEntity(int x, int y, String type) {
        super(
            "character-" + x + "-" + y, // id
            x, y, type
        );
        this.health = 100;
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
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }
}
