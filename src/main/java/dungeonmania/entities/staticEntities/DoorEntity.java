package dungeonmania.entities.staticEntities;

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
import dungeonmania.util.DungeonEntityJsonObject;

public class DoorEntity extends Entity implements IBlocker {
    private int keyNumber;
    private boolean isLocked;
    private KeyEntity key;
    private SunStoneEntity sun_stone;

    public DoorEntity(int keyNumber) {
        this(0, 0, keyNumber);
    }
    
    public DoorEntity(int x, int y, int keyNumber) {
        super(x, y, EntityTypes.DOOR);
        this.keyNumber = keyNumber;
        this.isLocked = true;
    }

    public DoorEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY(), info.getKey());
    }
    
    public int getKeyNumber() {
        return keyNumber;
    }

    private KeyEntity getKeyFromInventory(CharacterEntity player, int keyNumber) {
        // TODO move this to the inventory implementation
        return player
            .getInventory()
            .stream()
            .filter(e -> e.getType().equals(EntityTypes.KEY))
            .map(KeyEntity.class::cast)
            .filter(k -> k.getKeyNumber() == keyNumber)
            .findFirst()
            .orElse(null);
    }

    private SunStoneEntity getStoneFromInventory(CharacterEntity player) {
        return player
            .getInventory()
            .stream()
            .filter(e -> e.getType().equals(EntityTypes.SUN_STONE))
            .map(SunStoneEntity.class::cast)
            .findFirst()
            .orElse(null);
    }

    private void unlockWith(KeyEntity key, CharacterEntity player) {
        if (key != null) {
            key.used(player);
            this.isLocked = false;
        }
    }

    //SunStone unlock doors
    private void stoneUnlock(SunStoneEntity sun_stone, CharacterEntity player) {
        if (sun_stone != null) {
            this.isLocked = false;
        }
    }



    @Override
    public boolean isBlocking() {
        return this.isLocked;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        if (ent instanceof CharacterEntity) {
            CharacterEntity player = (CharacterEntity) ent;
            if (player.containedInInventory(EntityTypes.KEY)){
                this.key = getKeyFromInventory(player, this.keyNumber);
                this.unlockWith(key, player);
            }
            else if (player.containedInInventory(EntityTypes.SUN_STONE))
            {
                this.sun_stone = getStoneFromInventory(player);
                this.stoneUnlock(sun_stone, player); 
            }
        }
        return !this.isLocked;
    }

    @Override
    public EntityResponse getInfo() {
        if (isLocked) {
            return new EntityResponse(id, type, position, false);
        } else {
            return new EntityResponse(id, "unlocked_door", position, false);
        }
    }
}
