package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public abstract class Entity implements IEntity {
    protected Position position;
    protected String id;
    protected String type;
    
    protected Entity(int x, int y, int layer, String type) {
        this.type = type;
        this.position = new Position(x, y, layer);
        this.id = type + "-" + x + "-" + y + "-" + layer;
    }

    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, false);
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Position getPosition() {
        return this.position;
    }

	public boolean isInSamePositionAs(IEntity ent) {
        return this.getPosition().equals(ent.getPosition());
    }
}
