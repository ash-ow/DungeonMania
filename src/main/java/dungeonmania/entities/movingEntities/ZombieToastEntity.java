package dungeonmania.entities.movingEntities;

import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.util.RandomChance;


public class ZombieToastEntity extends Entity implements IBattlingEntity, IAutoMovingEntity {
    Random rand = new Random();
    Integer seed;
    private float armourEntityProbability = 0.2f;
    private ArmourEntity equipped;
    
    public ZombieToastEntity(int x, int y, int layer) {
        super(x, y, layer, "zombie_toast");
        if (RandomChance.getRandomBoolean((float) armourEntityProbability)) {
            equipped = new ArmourEntity();
        }
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
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        Direction direction = Direction.getRandomDirection(new Random(rand.nextInt()));
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

}
