package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public abstract class CollectableEntity extends Entity implements ICollectable {
    protected int durability = 1;

    protected CollectableEntity(int x, int y, EntityTypes type) {
        super(x, y, type);
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public void decrementDurability() {
        this.durability--;
    }
}
