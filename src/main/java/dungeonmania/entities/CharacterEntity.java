package dungeonmania.entities;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class CharacterEntity implements IMovingEntity  {
    private Position position;
    private String id;
    private String type;
    
    public CharacterEntity() {
        new CharacterEntity(0, 0, "CharacterType");
    }

    public CharacterEntity(int x, int y, String type) {
        this.id = "entity-" + x + "-" + y;
        this.position = new Position(x, y);
        this.type = type;
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

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
