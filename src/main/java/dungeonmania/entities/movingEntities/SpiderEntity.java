package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity {
    public SpiderEntity() {
        this(0, 0, 0);
    }
    
    public SpiderEntity(int x, int y, int layer) {
        super(x, y, layer, "spider");
    }

    @Override
    public boolean isPassable() {
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
        // TODO determine correct Spider damage
        return 2;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }
//endregion

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction) {
        // To do!!!!
        System.out.println("Oh shit that's a spider!");
        this.move(Direction.DOWN);
        return false;
    }
}
