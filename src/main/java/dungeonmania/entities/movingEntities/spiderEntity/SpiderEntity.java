package dungeonmania.entities.movingEntities.spiderEntity;

import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IAutoMovingEntity;
import dungeonmania.entities.movingEntities.IBattlingEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity, IAutoMovingEntity {
    private SpiderState spiderMovement;
    private Position firstPosition;
    private Integer movementCount = 0;

    public SpiderEntity() {
        this(0, 0, 0);
    }
    
    public SpiderEntity(int x, int y, int layer) {
        super(x, y, layer, "spider");
        spiderMovement = new SpiderClockwise();
        firstPosition = this.position;
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
    public void move(EntitiesControl entities, CharacterEntity player) {
        if (!spiderMovement.moveSpider(movementCount, this, entities)) {
            if (!this.position.equals(firstPosition) && movementCount > 0) {
                movementCount = (movementCount - 2) % 8;
                changeDirection();
            }
        } else {
            movementCount = (movementCount + 1) % 8;
        }
        if (this.position.equals(player.getPosition())) {
            interactWithPlayer(entities, Direction.NONE, player);
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
        Battle(entities, player);
        return true;
    }
}
