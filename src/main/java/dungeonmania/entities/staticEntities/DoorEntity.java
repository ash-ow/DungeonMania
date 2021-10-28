package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.ITicker;

public class DoorEntity extends Entity implements ITicker {
    private int keyNumber;

    public DoorEntity(int keyNumber) {
        this(0, 0, 0, keyNumber);
    }
    
    public DoorEntity(int x, int y, int layer, int keyNumber) {
        super(x, y, layer, "door");
        this.keyNumber = keyNumber;
    }
    
    @Override
    public boolean isPassable() {
        // TODO implement locking
        return false;
    }

    public boolean isLocked() {
        // TODO state machine for locked door
        return true;
    }
    
    public int getKeyNumber() {
        return keyNumber;
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        // get list of entities in current position
        // get the player from the list
        // search player for key
        // consume key and unlock
    }
}
