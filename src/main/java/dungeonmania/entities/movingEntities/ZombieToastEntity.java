package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToastEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity, IAutoMovingEntity {
    public ZombieToastEntity() {
        this(0, 0, 0);
    }
    
    public ZombieToastEntity(int x, int y, int layer) {
        super(x, y, layer, "zombieToast");
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
        Battle(entities, player);
        return false;
    }

// region Moving
    private Direction lastMovedDirection;

    @Override
    public void setLastMovedDirection(Direction direction) {
        this.lastMovedDirection = direction;
    }

    @Override
    public Direction getLastMovedDirection() {
        return this.lastMovedDirection;
    }
    
    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        // TODO Auto-generated method stub
        
    }
//endregion
}
