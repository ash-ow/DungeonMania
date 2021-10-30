package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity implements IInteractingEntity, IBattlingEntity, IAutoMovingEntity {

    private float health;
    private int damage;

    public MercenaryEntity() {
        this(0, 0, 0);
    }

    public MercenaryEntity(int x, int y, int layer) {
        super(x, y, layer, "mercenary");
        this.health = 100;
        this.damage = 3;
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
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        Battle(entities, player);
        return true;
    }

    

// region Moving
    private Direction lastMovedDirection;

    @Override
    public void setLastMovedDirection(Direction direction) {
        this.lastMovedDirection = direction;
    }

    @Override
    public Direction getLastMovedDirection() {
        return this.lastMovedDirection;
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        List<Direction> usefulDirections = getUsefuDirections(player);
        // TODO check player is invisible here
        moveToUsefulUnblocked(usefulDirections, entitiesControl);
        if (this.isInSamePositionAs(player)) {
            interactWithPlayer(entitiesControl, Direction.NONE, player);
        }
    }

    public void moveToUsefulUnblocked(List<Direction> usefulDirections, EntitiesControl entitiesControl) {
        for (Direction d : usefulDirections) {
            if (!targetPositionIsBlocked(d, entitiesControl)) {
                this.move(d);
                break;
            }
        }
    }

    private boolean targetPositionIsBlocked(Direction d, EntitiesControl entitiesControl) {
            Position target = this.position.translateBy(d);
            return EntitiesControl.containsBlockingEntities(entitiesControl.getAllEntitiesFromPosition(target));
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
    // endregion
    
}
