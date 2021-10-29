package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity, IAutoMovingEntity{

    private float health;
    private int damage;

    public MercenaryEntity(int x, int y, int layer) {
        super(x, y, layer, "mercenary");
        this.health = 20;
    }

    @Override
    public boolean isPassable() {
        return false;
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
        System.out.println("Oh shit that's a mercenary!");
        Battle(player);
        return true;
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        List<Direction> usefulDirections = getUsefuDirections(player);
        // TODO check player is invisible here
        if (usefulDirections.size() == 0) {
            return;
        }
        for (Direction d : usefulDirections) {
            Position target = this.position.translateBy(d);
            if (!isThereUnpassable(target, entitiesControl)) {
                this.move(d);
                return;
            }
        }
        if (this.position.equals(player.getPosition())) {
            interactWithPlayer(entitiesControl, Direction.NONE, player);
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
        return usefulDirections;    
    }
    
}
