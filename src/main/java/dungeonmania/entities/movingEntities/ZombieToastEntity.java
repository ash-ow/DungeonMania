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
import dungeonmania.util.DungeonEntityJsonParser;
import dungeonmania.util.Position;
import dungeonmania.util.RandomChance;

public class ZombieToastEntity extends Entity implements IBattlingEntity, IAutoMovingEntity {
    Random rand = new Random();
    Integer seed;
    private float armourEntityProbability = 0.4f;
    private ArmourEntity equipped;
    private IMovingBehaviour moveBehaviour;
    
    /**
     * Zombie Toast constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public ZombieToastEntity(int x, int y) {
        super(x, y, EntityTypes.ZOMBIE_TOAST);
        if (RandomChance.getRandomBoolean((float) armourEntityProbability)) {
            equipped = new ArmourEntity();
        }
        this.moveBehaviour = new RandomMove();
    }

    public ZombieToastEntity(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }
    
    /**
     * Zombie Toast constructor
     */
    public ZombieToastEntity() {
        this(0, 0);
    }
    
    /**
     * Zombie Toast constructor
     * @param x                        x-coordinate on the map
     * @param y                        y-coordinate on the map
     * @param layer                    layer on the map 
     * @param ArmourEntityProbability  determines the proability that an armour will be dropped
     * @param seed                     used to calculate probability
     */
    public ZombieToastEntity(int x, int y, float ArmourEntityProbability, int seed) {
        this(x, y);
        this.seed = seed;
        rand = new Random(seed);
        this.armourEntityProbability = ArmourEntityProbability;
        if (RandomChance.getRandomBoolean((float) armourEntityProbability)) {
            equipped = new ArmourEntity();
        }
        this.moveBehaviour = new RandomMove(seed);
    }

    /**
     * Moves the zombie toast
     * @param entitiesControl          list of all entities
     * @param player                   player used to determine the move behaviour                
     */
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

    public float loseHealth(float enemyHealth, float enemyDamage) {
        float damage = ((enemyHealth * enemyDamage) / 5);
        this.health -= damage;
        return damage;
    }
    //endregion
    
    /**
     * Drops relevant entities
     * @param player           the player which is battling and will and the entity added to their inventory
     */
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

    /**
     * Drops relevant entities, based on probability
     * @param player           the player which is battling and will and the entity added to their inventory
     * @param probaility       the probability of entities dropping
     */
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
