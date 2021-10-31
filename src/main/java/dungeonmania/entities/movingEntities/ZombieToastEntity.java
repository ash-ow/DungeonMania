package dungeonmania.entities.movingEntities;

import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToastEntity extends Entity implements IBattlingEntity, IAutoMovingEntity {
    Random rand = new Random();
    Integer seed;

    public ZombieToastEntity() {
        this(0, 0, 0);
    }
    
    public ZombieToastEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ZOMBIE_TOAST);
    }

    public ZombieToastEntity(int x, int y, int layer, int seed) {
        this(x, y, layer);
        this.seed = seed;
        rand = new Random(seed);
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        Direction direction = Direction.getRandomDirection(new Random(rand.nextInt()));
        // TODO this is the same as the players - could we abstract this to IMovingEntity or something?
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) ) {
            this.move(direction);
        }
        if (this.isInSamePositionAs(player)) {
            contactWithPlayer(entitiesControl, player);
        }
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

    public float getDamage() {
        // TODO determine correct ZombieToast damage
        return 3;
    }

    @Override
    public void loseHealth(float enemyHealth, float enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }
//endregion

}
