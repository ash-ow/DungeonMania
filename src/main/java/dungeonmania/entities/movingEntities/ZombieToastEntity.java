package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToastEntity extends Entity implements IInteractingEntity, IMovingEntity, IBattlingEntity {
    private float health = 100;

    public ZombieToastEntity() {
        super("ZombieToastType");
    }

    public ZombieToastEntity(int x, int y, String type) {
        super(
            "zombieToast-" + x + "-" + y, // id
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
        System.out.println("zombie toast?? For real?");
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
