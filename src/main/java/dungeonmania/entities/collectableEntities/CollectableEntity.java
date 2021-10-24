package dungeonmania.entities.collectableEntities;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public abstract class CollectableEntity implements ICollectableEntity {
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
