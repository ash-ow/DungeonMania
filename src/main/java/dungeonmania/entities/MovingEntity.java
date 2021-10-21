package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public class MovingEntity implements IEntity {
    public EntityResponse getInfo() {
        return null;
    }
    public Position getPosition() {
        return null;
    }
    public boolean passable() {
        return true;
    }
}
