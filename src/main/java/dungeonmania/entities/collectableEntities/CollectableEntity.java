package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IEntity;
import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public class CollectableEntity implements IEntity {
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
