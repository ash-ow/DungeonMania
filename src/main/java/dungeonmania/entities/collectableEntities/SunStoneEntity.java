package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class SunStoneEntity extends TreasureEntity implements ITreasure {

    public SunStoneEntity() {
        this(0,0);
    }

     /**
     * Sun Stone constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public SunStoneEntity(int x, int y) {
        super(x, y, EntityTypes.SUN_STONE);
    }

    public SunStoneEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
    
    /**
     * When sun_stone is used for bribing it is not removed from inventory and overrides the Treasure class implementation
     * @param player CharacterEntity using the potion
     */
    @Override
    public void used(CharacterEntity player) {
        player.removeEntityFromInventory(this);
    }  
    

    

}
