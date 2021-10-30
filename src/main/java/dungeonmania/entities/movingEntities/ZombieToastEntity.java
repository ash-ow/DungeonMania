package dungeonmania.entities.movingEntities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToastEntity extends Entity implements IInteractingEntity, IBattlingEntity, IAutoMovingEntity {
    Random rand = new Random();

    public ZombieToastEntity() {
        this(0, 0, 0);
    }
    
    public ZombieToastEntity(int x, int y, int layer) {
        super(x, y, layer, "zombie_toast");
    }

    public ZombieToastEntity(int x, int y, int layer, int seed) {
        this(x, y, layer);
        rand = new Random(seed);
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        Direction direction = getRandomDirection();
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if (!EntitiesControl.containsUnpassableEntities(targetEntities)) {
            this.move(direction);
        }
        if (this.isInSamePositionAs(player)) {
            interactWithPlayer(entitiesControl, Direction.NONE, player);
        }
    }

    private Direction getRandomDirection() {
        List<Direction> directions = Arrays.asList(Direction.RIGHT, Direction.DOWN, Direction.UP, Direction.LEFT);
        return directions.get(rand.nextInt(3));
    }

    @Override
    public boolean isPassable() {
        return true;
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
        Battle(entities, player);
        return false;
    }

}