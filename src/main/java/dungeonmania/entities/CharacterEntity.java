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

    @Override
    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, true);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
