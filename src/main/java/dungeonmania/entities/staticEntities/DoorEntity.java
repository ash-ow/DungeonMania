package dungeonmania.entities.staticEntities;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.collectableEntities.SunStoneEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;


public class DoorEntity extends Entity implements IBlocker {
    private int keyNumber;
    private boolean isLocked;
    private KeyEntity key;
    private SunStoneEntity sun_stone;

     /**
     * Door constructor
     * @param keyNumber denotes the door id 
     */
    public DoorEntity(int keyNumber) {
        this(0, 0, keyNumber);
    }
    
    /**
     * Door constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     * @param keyNumber denotes the door id which is unlockable by a key with corresponding keyNumber 
     */
    public DoorEntity(int x, int y, int keyNumber) {
        super(x, y, EntityTypes.DOOR);
        this.keyNumber = keyNumber;
        this.isLocked = true;
    }

    public DoorEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt(), jsonInfo.get("key").getAsInt());
    }
    
    public int getKeyNumber() {
        return keyNumber;
    }

    /**
     * Checks whether a key with a specific keyNumber is in the inventory and returns it
     * @param player       the player which should have key in inventory 
     * @param keyNumber    denotes which door the key should open
     */
    private KeyEntity getKeyFromInventory(CharacterEntity player, int keyNumber) {
        return player
            .getInventory()
            .stream()
            .filter(e -> e.getType().equals(EntityTypes.KEY))
            .map(KeyEntity.class::cast)
            .filter(k -> k.getKeyNumber() == keyNumber)
            .findFirst()
            .orElse(null);
    }

    /**
     * Checks whether the player has a sun_stone in inventory and returns either TRUE or FALSE
     * @param player       the player which should have sun_stone in inventory 
     */
    private boolean getStoneFromInventory(CharacterEntity player) {
        return player
            .getInventory()
            .stream()
            .anyMatch(e -> e.getType().equals(EntityTypes.SUN_STONE));
    }
    
    /**
     * Unlocks the door when key is in inventory and is removed from inventory after use 
     *  @param key          Key Entity 
     *  @param player       the player which should have key in inventory 
     */
    private void tryUnlockWithKey(KeyEntity key, CharacterEntity player) {
        if (key != null) {
            key.used(player);
            this.isLocked = false;
            this.type = EntityTypes.UNLOCKED_DOOR;
        }
    }

    /**
     * Unlocks the door as sun_stone is in inventory 
     *  @param sun_stone    Sun Stone Entity 
     *  @param player       the player which should have sun_stone in inventory 
     */
    private void tryUnlockWithStone(SunStoneEntity sun_stone, CharacterEntity player) {
            this.isLocked = false;
        }

    @Override
    public boolean isBlocking() {
        return this.isLocked;
    }

    /**
     *  Checks whether the conditions have been met to unlock the door thus unblocking player movement on that entity 
     * @param ent             the entity trying to move the entity
     * @param direction        direction the chatacter is moving
     * @param entitiesControl  list of all entities    
     */
    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        if (ent instanceof CharacterEntity) {
            CharacterEntity player = (CharacterEntity) ent;
            boolean stoneInInventory = getStoneFromInventory(player);
            if (player.containedInInventory(EntityTypes.KEY)){
                this.key = getKeyFromInventory(player, this.keyNumber);
                this.tryUnlockWithKey(key, player);
            }
            else if (stoneInInventory){
                this.tryUnlockWithStone(sun_stone, player); 
            }
        }
        return !this.isLocked;
    }

    @Override
    public JsonObject buildJson() {
        JsonObject entityInfo = super.buildJson();
        entityInfo.addProperty("key", this.keyNumber);
        return entityInfo;
    }
}
