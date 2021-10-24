package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity {
    public SpiderEntity() {
        super("SpiderType");
    }

    public SpiderEntity(int x, int y, String type) {
        super(
            "spider-" + x + "-" + y, // id
            x, y, type
        );
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void interactWithPlayer(CharacterEntity character) {
        System.out.println("Oh shit that's a spider!");
        character.move(Direction.DOWN);
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
        // TODO determine correct Spider damage
        return 2;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }
//endregion
}
