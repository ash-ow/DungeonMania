package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;

public abstract class CollectableEntity extends Entity implements ICollectableEntity {
    protected CollectableEntity(int x, int y, int layer, String type) {
        super(x, y, layer, type);
    }
}
