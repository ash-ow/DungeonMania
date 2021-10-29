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

    public void setPosition(Position position) {
        this.position = position;
    }

	public boolean isInSamePositionAs(IEntity ent) {
        return this.getPosition().equals(ent.getPosition());
    }

    public boolean isPassable() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {return true;}
        if (!(o instanceof Entity)) {return false;}
        Entity c = (Entity)o;
        return this.getId().equals(c.getId());
    }
}
