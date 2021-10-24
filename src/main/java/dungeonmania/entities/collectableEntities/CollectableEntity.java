package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public abstract class CollectableEntity implements IEntity {
    @Override
    public boolean passable() {
        return true;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EntityResponse getInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Position getPosition() {
        // TODO Auto-generated method stub
        return null;
    }
}
