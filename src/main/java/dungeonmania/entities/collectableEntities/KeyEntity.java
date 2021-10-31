package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class KeyEntity extends Entity implements ICollectableEntity {
    private int keyNumber;

    public KeyEntity(int keyNumber) {
        this(0, 0, 0, keyNumber);
    }

    public KeyEntity(int x, int y, int layer, int keyNumber) {
        super(x, y, layer, "key");
        this.keyNumber = keyNumber;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    @Override
    public void used(CharacterEntity player){
        player.removeEntityFromInventory(this);
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return false;
    } 
}
