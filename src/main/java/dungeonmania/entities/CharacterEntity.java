package dungeonmania.entities;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntity implements IEntity  {
    private Position position;
    private String id;
    private String type;
    

    public CharacterEntity(int x, int y, String type) {
        this.id = "entity-" + x + "-" + y;
        this.position = new Position(x, y);
        this.type = type;
    }

    public void move(Direction direction) {
        this.position = this.position.translateBy(direction);
    }

    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, true);
    }

    public Position getPosition() {
        return position;
    }
    
    public boolean passable() {
        return false;
    }
}
