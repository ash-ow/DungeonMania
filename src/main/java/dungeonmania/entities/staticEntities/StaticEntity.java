package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

import dungeonmania.entities.*;

import dungeonmania.response.models.*;

public abstract class StaticEntity implements IEntity{
    private Position position;
    private String id;
    private String type;

    protected StaticEntity(int x, int y, int layer, String type) {
        this.id = "Entities-" + x + "-" + y + "-" + layer;
        this.position = new Position(x, y, layer);
        this.type = type;
    }
    public EntityResponse getInfo() {
        return new EntityResponse(id, type, position, false);
    }
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public boolean passable() {
        return true;
    }
    public String getId() {
        return this.id;
    }
}
