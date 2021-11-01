package dungeonmania.entities.movingEntities;

import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;
import dungeonmania.entities.movingEntities.moveBehaviour.RandomMove;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.util.RandomChance;


public class ZombieToastEntity extends Entity implements IBattlingEntity, IAutoMovingEntity {
    Random rand = new Random();
    Integer seed;
    private float armourEntityProbability = 0.4f;
    private ArmourEntity equipped;
    private IMovingBehaviour moveBehaviour;
    
    public ZombieToastEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.ZOMBIE_TOAST);
        if (RandomChance.getRandomBoolean((float) armourEntityProbability)) {
            equipped = new ArmourEntity();
        }
        this.moveBehaviour = new RandomMove();
    }
    
    public ZombieToastEntity() {
        this(0, 0, 0);
    }
    
    public ZombieToastEntity(int x, int y, int layer, float ArmourEntityProbability, int seed) {
        this(x, y, layer);
        this.seed = seed;
        rand = new Random(seed);
        this.armourEntityProbability = ArmourEntityProbability;
        if (RandomChance.getRandomBoolean((float) armourEntityProbability)) {
            equipped = new ArmourEntity();
        }
        this.moveBehaviour = new RandomMove(seed);
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        this.move(moveBehaviour.getBehaviourDirection(entitiesControl, player, position));      
        if (this.isInSamePositionAs(player)) {
            contactWithPlayer(entitiesControl, player);
        }
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

//region Description
    private float health = 50;

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    public float getDamage() {
        return 3;
    }

    public void loseHealth(float enemyHealth, float enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }
    //endregion
    @Override
    public void dropEntities(CharacterEntity player) {
        OneRingEntity ring = new OneRingEntity();
        if (RandomChance.getRandomBoolean(ring.getDropChance())) {
            player.addEntityToInventory(ring);
        }
        if (equipped != null) {
            player.addEntityToInventory(equipped);
        }
    }

    @Override
    public void dropEntities(CharacterEntity player, float probability) {
        OneRingEntity ring = new OneRingEntity();
        ArmourEntity armour = new ArmourEntity();
        if (RandomChance.getRandomBoolean(probability)) {
            player.addEntityToInventory(ring);
        }
        if (equipped != null) {
            player.addEntityToInventory(armour);
        }
    }

    @Override
    public void setMoveBehvaiour(IMovingBehaviour newBehaviour) {
        this.moveBehaviour = newBehaviour;        
    }

}
