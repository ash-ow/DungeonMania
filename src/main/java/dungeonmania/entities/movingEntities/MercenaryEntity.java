package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity implements IContactingEntity, IBattlingEntity, IAutoMovingEntity, IInteractingEntity{

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
    public boolean isPassable() {
        return true;
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
    public int getDamage() {
        return damage;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;        
    }

    @Override
    public boolean contactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        Battle(entities, player);
        return true;
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        List<Direction> usefulDirections = getUsefuDirections(player);
        // TODO check player is invisible here
        moveToUsefulUnblocked(usefulDirections, entitiesControl);
        if (this.isInSamePositionAs(player)) {
            contactWithPlayer(entitiesControl, Direction.NONE, player);
        }
    }

    public void moveToUsefulUnblocked(List<Direction> usefulDirections, EntitiesControl entitiesControl) {
        for (Direction d : usefulDirections) {
            Position target = this.position.translateBy(d);
            if (!isThereUnpassable(target, entitiesControl)) {
                this.move(d);
                break;
            }
        }
    }

    private boolean isThereUnpassable(Position position, EntitiesControl entitiesControl) {
        return EntitiesControl.containsUnpassableEntities(entitiesControl.getAllEntitiesFromPosition(position));
    }

    private List<Direction> getUsefuDirections(CharacterEntity player) {
        Position diff = Position.calculatePositionBetween(this.position, player.getPosition());
        List<Direction> usefulDirections = new ArrayList<>();
        if (diff.getX() < 0) {
            usefulDirections.add(Direction.LEFT);
        } else if (diff.getX() > 0) {
            usefulDirections.add(Direction.RIGHT);
        } 
        if (diff.getY() < 0) {
            usefulDirections.add(Direction.UP);
        } else if (diff.getY() > 0) {
            usefulDirections.add(Direction.DOWN);
        }
        if (usefulDirections.isEmpty()) {
            usefulDirections.add(Direction.NONE);
        }
        return usefulDirections;    
    }

    @Override
    public void interactWith(CharacterEntity player) throws InvalidActionException {
        List<IEntity> treasureInventory = player.getInventory().entitiesOfType("treasure");
        if (treasureInventory.isEmpty()) {
            throw new InvalidActionException("Player has no treasure");
        }
        if (!isInRange(player)) {
            throw new InvalidActionException("Player is too far away");
        }
        player.removeEntityFromInventory(treasureInventory.get(0));
        this.isBribed = true;       
    }

    public boolean isInRange(CharacterEntity player) {
        Position diff = Position.calculatePositionBetween(this.getPosition(), player.getPosition());
        int sum = Math.abs(diff.getX()) + Math.abs(diff.getY());
        return sum <= 2;
    }
}
