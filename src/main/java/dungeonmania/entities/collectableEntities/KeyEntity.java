package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonParser;

public class KeyEntity extends CollectableEntity {
    private int keyNumber;

    /**
     * Key constructor
     * @param keyNumber denotes which door the key should open
     */
    public KeyEntity(int keyNumber) {
        this(0, 0, keyNumber);
    }

    /**
     * Key constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param layer layer on the map
     * @param keyNumber denotes which door the key should open 
     */
    public KeyEntity(int x, int y, int keyNumber) {
        super(x, y, EntityTypes.KEY);
        this.keyNumber = keyNumber;
    }

    public KeyEntity(DungeonEntityJsonParser info) {
        this(info.getPosition().getX(), info.getPosition().getY(), info.getKey());
    }

    public int getKeyNumber() {
        return keyNumber;
    } 
}
