package dungeonmania.entities.movingEntities;

import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;
import dungeonmania.entities.movingEntities.moveBehaviour.RandomMove;
import dungeonmania.util.DungeonEntityJsonObject;
import dungeonmania.util.RandomChance;

public class HydraEntity extends Entity implements IBoss, IAutoMovingEntity {
    Integer seed;
    protected float health;
    private IMovingBehaviour moveBehaviour;
    private boolean specialAbility = true;

    public HydraEntity(int x, int y) {
        super(x, y, EntityTypes.HYDRA);
        Random rand = new Random();
        this.seed = rand.nextInt();
        this.setHealth(30);
        this.moveBehaviour = new RandomMove();
    }

    public HydraEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    public HydraEntity() {
        this(0, 0);
    }

    public HydraEntity(int x, int y, int seed) {
        this(x, y);
        this.moveBehaviour = new RandomMove(seed);
    }

    @Override
    public float loseHealth(float enemyHealth, float enemyDamage) {
        float damage = ((enemyHealth * enemyDamage) / 5);
        growNewHead(damage);
        return damage;
    }

    public float loseHealth(float enemyHealth, float enemyDamage, int seed) {
        this.seed = seed;
        float damage = ((enemyHealth * enemyDamage) / 5);
        growNewHead(damage);
        return damage;
    }

    private void growNewHead(float damage) {
        float lossHealthProbability = ((specialAbility) ? 0.5f : 1f);
        if (RandomChance.getRandomBoolean(lossHealthProbability, this.seed)) {
            this.health -= damage;
        } else {
            this.health += damage;
        }
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
        
    }

    @Override
    public float getDamage() {
        return 2;
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        this.move(moveBehaviour.getBehaviourDirection(entitiesControl, player, position));      
        if (this.isInSamePositionAs(player)) {
            contactWithPlayer(entitiesControl, player);
        }
        
    }

    @Override
    public void setMoveBehaviour(IMovingBehaviour newBehaviour) {
        this.moveBehaviour = newBehaviour;
    }

    @Override
    public void deactivteSpecialAbility() {
        this.specialAbility = false;
    }
}
