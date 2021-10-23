package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public abstract class Entity implements IEntity {
    protected Position position;
    private String id;
    private String type;

    public Entity(String type) {
        this(0, 0, type);
    }
    
    protected Entity(int x, int y, String type) {
        this.id = "entity-" + x + "-" + y;
        this.position = new Position(x, y);
        this.type = type;
    }

    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, true);
    }

    public Position getPosition() {
        return this.position;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }
}
