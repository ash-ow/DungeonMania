package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.staticEntities.DoorEntity;

public class KeyEntity extends Entity implements ICollectableEntity {
    private int keyNumber;

    public KeyEntity(int keyNumber) {
        this(0, 0, 0, keyNumber);
    }

    public KeyEntity(int x, int y, int layer, int keyNumber) {
        super(x, y, layer, "key");
        this.keyNumber = keyNumber;
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    @Override
    public void used(CharacterEntity player){
        // TODO decrement the keys in the inventory by 1
        // TODO inventory can contain only one key at a time
        this.position = player.getPosition();
    }

    public boolean unlocks(DoorEntity door) {
        return this.keyNumber == door.getKeyNumber();
    }
}
