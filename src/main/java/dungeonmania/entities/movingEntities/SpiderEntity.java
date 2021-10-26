package dungeonmania.entities.movingEntities;

import java.util.Arrays;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity {
    private List<Direction> movementPattern = Arrays.asList(Direction.RIGHT, Direction.DOWN, Direction.DOWN, Direction.LEFT, 
    Direction.LEFT, Direction.UP, Direction.UP, Direction.RIGHT);

    private Direction nextDirection;
    private Integer movementPatternIndex;

    public SpiderEntity() {
        this(0, 0, 0);
    }
    
    public SpiderEntity(int x, int y, int layer) {
        super(x, y, layer, "spider");
        nextDirection = Direction.UP;
        movementPatternIndex = 0;
    }

    @Override
    public void move(Direction direction, int layer) {
        direction = nextDirection;
        nextDirection = movementPattern.get(movementPatternIndex);
        movementPatternIndex = (movementPatternIndex + 1) % 8;
        this.setPosition(
            this.getPosition().translateBy(direction).asLayer(layer)
        );
    }

    @Override
    public boolean isPassable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

//region Battle
    private float health = 100;

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    public int getDamage() {
        // TODO determine correct Spider damage
        return 2;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 5);
    }
//endregion

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        // To do!!!!
        System.out.println("Oh shit that's a spider!");
        this.move(Direction.DOWN);
        //player.move(direction);
        return true;
    }
}
