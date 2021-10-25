package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToastEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity {
    public ZombieToastEntity() {
        super("ZombieToastType");
    }

    public ZombieToastEntity(int x, int y, String type) {
        super(
            "zombieToast-" + x + "-" + y, // id
            x, y, type
        );
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
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
        // TODO determine correct ZombieToast damage
        return 3;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }
//endregion

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        // To do!!!!
        System.out.println("zombie toast?? For real?");
        this.move(Direction.DOWN);
        return false;
    }
}
