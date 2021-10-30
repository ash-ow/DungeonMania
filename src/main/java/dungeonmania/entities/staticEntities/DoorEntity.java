package dungeonmania.entities.staticEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;

public class DoorEntity extends Entity implements IBlocker {
    private int keyNumber;
    private boolean isLocked;
    private KeyEntity key;

    public DoorEntity(int keyNumber) {
        this(0, 0, 0, keyNumber);
    }
    
    public DoorEntity(int x, int y, int layer, int keyNumber) {
        super(x, y, layer, "door");
        this.keyNumber = keyNumber;
        this.isLocked = true;
    }
    
    public int getKeyNumber() {
        return keyNumber;
    }

    private KeyEntity getKeyFromInventory(CharacterEntity player, int keyNumber) {
        // TODO move this to the inventory implementation
        return player
            .getInventory()
            .getAllEntitiesOfType("key")
            .stream()
            .map(KeyEntity.class::cast)
            .filter(k -> k.getKeyNumber() == keyNumber)
            .findFirst()
            .orElse(null);
    }

    private void unlockWith(KeyEntity key, CharacterEntity player) {
        if (key != null) {
            key.used(player);
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
            this.key = getKeyFromInventory(player, this.keyNumber);
            this.unlockWith(key, player);
        }
        return !this.isLocked;
    }
}
