package dungeonmania.entities.collectableEntities;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;


public class BombEntity extends CollectableEntity implements ITicker, IBlocker {
    boolean isArmed = false;

    /**
     * Bomb constructor
     */
    public BombEntity() {
        this(0, 0);
    }
    
    /**
     * Bomb constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public BombEntity(int x, int y) {
        super(x, y, EntityTypes.BOMB);
    }

    public BombEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }
    
    /**
     * Getter for whether a bomb is armed
     */
    public boolean isArmed() {
        return this.isArmed;
    }
    
    /**
     * Arms the bomb and removes it from inventory
     * @param player the characterEntity who is using the bomb
     */
    @Override
    public void used(CharacterEntity player){
        this.isArmed = true;
        player.removeEntityFromInventory(this);
    }

    /**
     * Picks up the bomb if it is not armed
     * @param player    the characterEntity who is picking up the bomb
     * @param entities  list of entities which the bomb will be reduced from
     */
    @Override    
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        if (!this.isArmed) {
            player.addEntityToInventory(this);
            entities.removeEntity(this);
        }
    }

    /**
     * Explodes the bomb after tick is called
     * @param entitiesControl list of entities which the bomb could affect
     */
    @Override
    public void tick(EntitiesControl entitiesControl) {
        if (this.isArmed) {
            explode(entitiesControl);
        }
    }

    /**
     * Explodes the bomb by removing entities
     * @param entitiesControl list of entities which the bomb affects
     */
    private void explode(EntitiesControl entitiesControl) {
        List<IEntity> adjacentEntities = entitiesControl.getAllAdjacentEntities(this.getPosition());
        adjacentEntities.addAll(entitiesControl.getAllEntitiesFromPosition(this.getPosition()));
        if (containsActiveSwitch(adjacentEntities)) {
            for (IEntity entity : adjacentEntities) {
                explodeNonCharacterEntity(entity, entitiesControl);               
            }
        }
    }

    /**
     * Removes entity if they aren't a character entity
     * @param entitiesControl list of entities which the bomb affects
     * @param entity          entity which is removed
     */
    private void explodeNonCharacterEntity(IEntity entity, EntitiesControl entitiesControl) {
        if (!(entity instanceof CharacterEntity)) {
            entitiesControl.removeEntity(entity);
        }
    }

    @Override
    public boolean isPlacedAfterUsing() {
        return true;
    }

// region Blocking
    @Override
    public boolean isBlocking() {
        return isArmed;
    }

    @Override
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        // Bombs cannot be unblocked once placed
        return false;
    }
// endregion
}
