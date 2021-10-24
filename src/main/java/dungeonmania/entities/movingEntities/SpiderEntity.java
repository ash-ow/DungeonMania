package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity {
    private float health;

    public SpiderEntity() {
        super("SpiderType");
    }

    public SpiderEntity(int x, int y, String type) {
        super(
            "spider-" + x + "-" + y, // id
            x, y, type
        );
        this.health = 100;
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void interactWithPlayer(CharacterEntity character) {
        System.out.println("Oh shit that's a spider!");
        character.move(Direction.DOWN);
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }
}
