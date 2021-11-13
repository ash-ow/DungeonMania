package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;


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
     * @param keyNumber denotes which door the key should open 
     */
    public KeyEntity(int x, int y, int keyNumber) {
        super(x, y, EntityTypes.KEY);
        this.keyNumber = keyNumber;
    }

    public KeyEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt(), jsonInfo.get("key").getAsInt());
    }

    public int getKeyNumber() {
        return keyNumber;
    } 

    @Override
    public JsonObject buildJson() {
        JsonObject entityInfo = super.buildJson();
        entityInfo.addProperty("key", this.keyNumber);
        return entityInfo;
    }
}
