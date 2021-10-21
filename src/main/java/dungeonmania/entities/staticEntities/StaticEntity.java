package dungeonmania.entities.staticEntities;

import org.w3c.dom.Entity;

import dungeonmania.util.Position;

import dungeonmania.entities.*;

import dungeonmania.response.models.*;

public abstract class StaticEntity implements IEntity{
    private Position position;
    private String id;
    private String type;
    public StaticEntity(int x, int y, String type) {
        this.id = "entity-" + x + "-" + y;
        this.position = new Position(x, y);
        this.type = type;
    }
    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, false);
    }
}
