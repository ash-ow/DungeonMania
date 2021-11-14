package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;


public class TreasureEntity extends CollectableEntity {
    /**
     * Treasure constructor
     */
    public TreasureEntity() {
        this(0, 0);
    }
    
    /**
     * Treasure constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public TreasureEntity(int x, int y) {
        this(x, y, EntityTypes.TREASURE);
    }

    public TreasureEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    public TreasureEntity(int x, int y, EntityTypes type) {
        super(x, y, type);
    }

     /**
     * When treasure is used for bribing it is removed from the inventory 
     * @param player CharacterEntity using the treasure
     */
    public void used(CharacterEntity player) {
        player.removeEntityFromInventory(this);
    }   

}
