package dungeonmania.entities.movingEntities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.buildableEntities.ShieldEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.util.RandomChance;


public class ZombieToastEntity extends Entity implements IInteractingEntity, IAutoMovingEntity, IDroppingEntities {
    Random rand = new Random();
    Integer seed;
    private float armourEntityProbability = 0.2f;
    private ArmourEntity equipped;
    private float dropProbability = 0.1f;
    
    
    public ZombieToastEntity(int x, int y, int layer) {
        super(x, y, layer, "zombie_toast");
        if (RandomChance.getRandomBoolean((float) armourEntityProbability)) {
            equipped = new ArmourEntity();
        }
    }
    
    public ZombieToastEntity(int x, int y, int layer, float ArmourEntityProbability, float dropProbability) {
        this(x, y, layer);
        this.armourEntityProbability = ArmourEntityProbability;
        if (RandomChance.getRandomBoolean((float) armourEntityProbability)) {
            equipped = new ArmourEntity();
        }
        this.dropProbability = dropProbability;
    }
    
    public ZombieToastEntity() {
        this(0, 0, 0);
    }

    public ZombieToastEntity(int x, int y, int layer, int seed) {
        this(x, y, layer);
        this.seed = seed;
        rand = new Random(seed);
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        Direction direction = Direction.getRandomDirection(new Random(rand.nextInt()));
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if (!EntitiesControl.containsUnpassableEntities(targetEntities)) {
            this.move(direction);
        }
        if (this.isInSamePositionAs(player)) {
            interactWithPlayer(entitiesControl, Direction.NONE, player);
        }
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean checkEnemyDeath(EntitiesControl entitiesControl, CharacterEntity player) {
        if (!this.isAlive()) {
            if (RandomChance.getRandomBoolean(dropProbability)){
                player.addEntityToInventory(new OneRingEntity());
            }
            if (equipped != null) {
                player.addEntityToInventory(equipped);
            }
            entitiesControl.removeEntity(this);
            return true;
        }
        return false;
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
        return 1;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        if (equipped != null) {
            this.health -= ((enemyHealth * enemyDamage) / 10);
        } else {
            this.health -= ((enemyHealth * enemyDamage) / 5);
        }
    }
//endregion

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        Battle(entities, player);
        return false;
    }

    @Override
    public void setDropProbability(float probability) {
        this.dropProbability = probability;
    }

    public float getArmourEntityProbability() {
        return ArmourEntityProbability;
    }

    public void setArmourEntityProbability(float armourEntityProbability) {
        ArmourEntityProbability = armourEntityProbability;
    }

}