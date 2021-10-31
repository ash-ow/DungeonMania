package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public abstract class CollectableEntity extends Entity implements ICollectableEntity {
    protected CollectableEntity(int x, int y, int layer, EntityTypes type) {
        super(x, y, layer, type);
    }
}
