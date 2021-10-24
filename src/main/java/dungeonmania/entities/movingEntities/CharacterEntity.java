package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    public CharacterEntity() {
        super("CharacterType");
    }

    public CharacterEntity(int x, int y, String type) {
        super(
            "character-" + x + "-" + y, // id
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

//region Description
    private float health = 100;

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    public int getDamage() {
        // TODO determine correct Character damage
        return 3;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 10);
    }
//endregion
}
