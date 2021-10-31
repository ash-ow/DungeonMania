package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity implements IBattlingEntity, IAutoMovingEntity {

    private float health;
    private int damage;
    private boolean isBribed;

    public MercenaryEntity() {
        this(0, 0, 0);
    }

    public MercenaryEntity(int x, int y, int layer) {
        super(x, y, layer, "mercenary");
        this.health = 100;
        this.damage = 3;
        this.isBribed = false;
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
    public void loseHealth(float enemyHealth, float enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;        
    }

    @Override
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        if (!this.isBribed) {
            battle(entities, player);
        }       
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        if (isBribed) {
            setPosition(player.getPreviousPosition());
        } else {
            List<Direction> usefulDirections = getUsefuDirections(player);
            // TODO check player is invisible here
            moveToUsefulUnblocked(usefulDirections, entitiesControl);
            if (this.isInSamePositionAs(player)) {
                contactWithPlayer(entitiesControl, player);
            }
        }       
    }



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

    public boolean isInRange(CharacterEntity player) {
        Position diff = Position.calculatePositionBetween(this.getPosition(), player.getPosition());
        int sum = Math.abs(diff.getX()) + Math.abs(diff.getY());
        return sum <= 2;
    }
}
