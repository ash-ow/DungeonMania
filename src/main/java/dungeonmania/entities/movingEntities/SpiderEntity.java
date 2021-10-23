package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class SpiderEntity extends Entity implements IInteractingEntity {
    public SpiderEntity() {
        super("SpyderType");
        //TODO Auto-generated constructor stub
    }

    public SpiderEntity(int x, int y, String type) {
        super(x, y, type);
        //TODO Auto-generated constructor stub
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
