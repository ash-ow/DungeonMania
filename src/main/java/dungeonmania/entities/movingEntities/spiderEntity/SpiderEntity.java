package dungeonmania.entities.movingEntities.spiderEntity;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IAutoMovingEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;
import dungeonmania.util.DungeonEntityJsonObject;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IBattlingEntity, IAutoMovingEntity {
    private SpiderState spiderMovement;
    private Position firstPosition;
    private Integer movementCount = 0;
    private IMovingBehaviour moveBehaviour;

    /**
     * Spider constructor
     */
    public SpiderEntity() {
        this(0, 0);
    }
    
    /**
     * Spider constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public SpiderEntity(int x, int y) {
        super(x, y, EntityTypes.SPIDER);
        spiderMovement = new SpiderClockwise();
        firstPosition = this.position;
    }

    public SpiderEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

// region Moving

    /**
     * Moves the spider
     * @param entities      the list of entities which the spider may interact with
     * @param player        player which the spider may come into contact with
     */
    @Override
    public void move(EntitiesControl entities, CharacterEntity player) {
        if (moveBehaviour == null) {
            if (!spiderMovement.moveSpider(movementCount, this, entities)) {
                if (!this.position.equals(firstPosition) && movementCount > 0) {
                    movementCount = (movementCount - 2) % 8;
                    changeDirection();
                }
            } else {
                movementCount = (movementCount + 1) % 8;
            }
            if (this.isInSamePositionAs(player)) {
                contactWithPlayer(entities, player);
            }
        } else {
            this.move(moveBehaviour.getBehaviourDirection(entities, player, position));
        }       
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    private SpiderState changeDirection() {
        if (spiderMovement instanceof SpiderClockwise) {
            spiderMovement = new SpiderAntiClockwise(movementCount);
        } else {
            spiderMovement = new SpiderClockwise(movementCount);
        }
        return spiderMovement;
    }
    
//endregion

//region Battle
    private float health = 35;

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    public float getDamage() {
        return 2;
    }

    @Override
    public void loseHealth(float enemyHealth, float enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }
//endregion

    @Override
    public void setMoveBehvaiour(IMovingBehaviour newBehaviour) {
        this.moveBehaviour = newBehaviour;        
    }
}
