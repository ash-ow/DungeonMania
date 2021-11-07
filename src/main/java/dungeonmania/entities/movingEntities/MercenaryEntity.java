package dungeonmania.entities.movingEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.moveBehaviour.FollowPlayer;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.DungeonEntityJsonObject;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity implements IBattlingEntity, IAutoMovingEntity {

    private float health;
    private float damage;
    private boolean isBribed;
    private IMovingBehaviour moveBehaviour;

    /**
     * Mercenary constructor
     */
    public MercenaryEntity() {
        this(0, 0);
    }

    /**
     * Mercenary constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map 
     */
    public MercenaryEntity(int x, int y) {
        super(x, y, EntityTypes.MERCENARY);
        this.health = 70;
        this.damage = 3;
        this.isBribed = false;
        this.moveBehaviour = new FollowPlayer();
    }

    public MercenaryEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, !isBribed);
    }

    public boolean isBribed() {
        return isBribed;
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    @Override
    public float getDamage() {
        return damage;
    }

    @Override
    public float loseHealth(float enemyHealth, float enemyDamage) {
        float damage = ((enemyHealth * enemyDamage) / 5);
        this.health -= damage;
        return damage;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;        
    }

    /**
     * Interactions with player; will only do battle if the mercenary is not bribed
     * @param entitiesControl  the list of all entities
     * @param player           the player which is battling or has bribed the mercenary
     */
    @Override
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        if (!this.isBribed) {
            battle(entities, player);
        }       
    }

    /**
     * Moves the mercenary based on whether they are bribed by the player
     * @param entitiesControl  the list of all entities
     * @param player           the player 
     */
    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        if (isBribed) {
            setPosition(player.getPreviousPosition());
        } else if (!player.isInvisible()){
            this.move(moveBehaviour.getBehaviourDirection(entitiesControl, player, position));
            if (this.isInSamePositionAs(player)) {
                contactWithPlayer(entitiesControl, player);
            }
        } else {
            this.move(Direction.NONE);
        } 
    }

    /**
     * Determines the interactions of the mercenary with the player based on range and whether they have treasure
     * @param player the player with which the mercenary will interact with 
     */
    public void interactWith(CharacterEntity player) throws InvalidActionException {
        IEntity treasureFound = EntitiesControl.getFirstEntityOfType(player.getInventory(), TreasureEntity.class);
        if (treasureFound == null) {
            throw new InvalidActionException("Player has no treasure");
        }
        if (!isInRange(player)) {
            throw new InvalidActionException("Player is too far away");
        }
        player.removeEntityFromInventory(treasureFound);
        player.addTeammates(this);
        this.isBribed = true;       
    }

    /**
     * Determines if the player is in range of the mercenary
     * @param player the player with which the mercenary will interact with 
     * @return true if in range; false if not
     */
    public boolean isInRange(CharacterEntity player) {
        Position diff = Position.calculatePositionBetween(this.getPosition(), player.getPosition());
        int sum = Math.abs(diff.getX()) + Math.abs(diff.getY());
        return sum <= 2;
    }
    // endregion
    
    @Override
    public void setMoveBehvaiour(IMovingBehaviour newBehaviour) {
        this.moveBehaviour = newBehaviour;        
    }
}
