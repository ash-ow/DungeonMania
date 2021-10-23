package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class ZombieToastEntity extends Entity implements IInteractingEntity, IMovingEntity {
    public ZombieToastEntity() {
        super("ZombieToastType");
    }

    public ZombieToastEntity(int x, int y, String type) {
        super(
            "zombieToast-" + x + "-" + y, // id
            x, y, type
        );
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
}
