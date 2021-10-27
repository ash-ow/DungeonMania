package dungeonmania.entities.movingEntities;

import java.util.Arrays;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity {
    private Integer movementPatternIndex;
    private SpiderState spiderMovement;

    public SpiderEntity() {
        this(0, 0, 0);
    }
    
    public SpiderEntity(int x, int y, int layer) {
        super(x, y, layer, "spider");
        movementPatternIndex = 0;
        spiderMovement = new SpiderClockwise();
    }

    @Override
    public void move(Direction direction, EntitiesControl entities, CharacterEntity player) {
        if (!spiderMovement.moveSpider(movementPatternIndex, this, entities)) {
            movementPatternIndex = (movementPatternIndex - 3) % 8;
            if (spiderMovement instanceof SpiderClockwise) {
                spiderMovement = new SpiderAntiClockwise(movementPatternIndex);
            } else {
                spiderMovement = new SpiderClockwise(movementPatternIndex);
            }
        } 
        
        movementPatternIndex = (movementPatternIndex + 1) % 8;
    }

    @Override
    public boolean isPassable() {
        return true;
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
        this.move(Direction.DOWN, entities, player);
        //player.move(direction);
        return true;
    }
}
