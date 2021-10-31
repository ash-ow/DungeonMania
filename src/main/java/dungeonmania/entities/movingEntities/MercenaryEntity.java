package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity implements IInteractingEntity, IDroppingEntities, IAutoMovingEntity{

    private float health;
    private int damage;
    private float dropProbability = 0.1f;

    public MercenaryEntity() {
        this(0, 0, 0);
    }

    public MercenaryEntity(int x, int y, int layer) {
        super(x, y, layer, "mercenary");
        this.health = 100;
        this.damage = 3;
    }

    @Override
    public boolean isPassable() {
        return true;
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
    public void setDropProbability(float probability) {
        this.dropProbability = probability;
    }
    
}
