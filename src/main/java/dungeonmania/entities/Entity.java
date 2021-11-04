package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public abstract class Entity implements IEntity {
    protected Position position;
    protected String id;
    protected EntityTypes type;
    
    /**
     * Entity constructor
     * @param x      x-coordinate on the map
     * @param y      y-coordinate on the map
     * @param layer  layer on the map
     * @param type   type of entity 
     */
    protected Entity(int x, int y, EntityTypes type) {
        this.type = type;
        this.position = new Position(x, y);
        this.id = type + "-" + x + "-" + y + "-" + this.position.getLayer();
    }

    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, false);
    }

    public String getId() {
        return this.id;
    }

    public EntityTypes getType() {
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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {return true;}
        if (!(o instanceof Entity)) {return false;}
        Entity c = (Entity)o;
        return this.getId().equals(c.getId());
    }
}
