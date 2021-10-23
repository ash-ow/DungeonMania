package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity {
    public SpiderEntity() {
        super("SpiderType");
    }

    public SpiderEntity(int x, int y, String type) {
        super(
            "spider-" + x + "-" + y, // id
            x, y, type
        );
    }

    @Override
    public EntityResponse getInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Position getPosition() {
        return this.position;
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
}
