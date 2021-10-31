package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;

public class KeyEntity extends Entity implements ICollectableEntity {
    private int keyNumber;

    public KeyEntity(int keyNumber) {
        this(0, 0, 0, keyNumber);
    }

    public KeyEntity(int x, int y, int layer, int keyNumber) {
        super(x, y, layer, EntityTypes.KEY);
        this.keyNumber = keyNumber;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    } 
}
