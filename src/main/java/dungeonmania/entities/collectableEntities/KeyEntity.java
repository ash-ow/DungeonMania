package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;


public class KeyEntity extends CollectableEntity implements IBlocker {
    private int keyNumber;
    protected int durability = 1;
    private boolean keyPickedUp;

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

    /**
     * Picks up the key if player has no other key
     * @param player    the characterEntity who is picking up the key
     * @param entities  list of entities which the key will be removed from dunegeon 
     */
    @Override    
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        if (!this.keyPickedUp) {
            player.addEntityToInventory(this);
            entities.removeEntity(this);
        }
    }

    @Override
    public boolean isBlocking() {
        return this.keyPickedUp;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        return !this.keyPickedUp;
      
    }
    
}
