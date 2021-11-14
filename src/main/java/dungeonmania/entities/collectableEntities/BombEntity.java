package dungeonmania.entities.collectableEntities;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.Logic;
import dungeonmania.entities.LogicEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;


public class BombEntity extends LogicEntity implements ICollectable, IBlocker {
    boolean isArmed = false;
    protected int durability = 1;

    public BombEntity(Logic logic) {
        this(0, 0, logic);
    }
    
    public BombEntity(int x, int y, Logic logic) {
        super(x, y, logic, EntityTypes.BOMB);
        if (logic == Logic.NOT) {
            throw new InvalidActionException("Bomb cannot be created with \"not\" logic");
        }
    }

    public BombEntity(JsonObject info) {
        this(
            info.get("x").getAsInt(),
            info.get("y").getAsInt(),
            Logic.getLogicFromJsonObject(info)
        );
    }
    
    /**
     * Getter for whether a bomb is armed
     */
    public boolean isArmed() {
        return this.isArmed;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public void decrementDurability() {
        this.durability--;
    }
    
    /**
     * Arms the bomb and removes it from inventory
     * @param player the characterEntity who is using the bomb
     */
    @Override
    public void used(CharacterEntity player){
        this.isArmed = true;
        player.getInventoryItems().remove(this);
    }

    /**
     * Picks up the bomb if it is not armed
     * @param player    the characterEntity who is picking up the bomb
     * @param entities  list of entities which the bomb will be reduced from
     */
    @Override    
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        if (!this.isArmed) {
            player.getInventoryItems().add(this);
            entities.removeEntity(this);
        }
    }

    /**
     * Explodes the bomb by removing entities
     * @param entitiesControl
     */
    private void explode(EntitiesControl entitiesControl) {
        List<IEntity> adjacentEntities = entitiesControl.getAllAdjacentEntities(this.getPosition());
        adjacentEntities.addAll(entitiesControl.getAllEntitiesFromPosition(this.getPosition()));
        explodeNonCharacterEntities(adjacentEntities, entitiesControl);               
    }

    /**
     * Removes entity if they aren't a character entity
     * @param entitiesControl
     * @param entity entity which is removed
     */
    private void explodeNonCharacterEntities(List<IEntity> adjacentEntities, EntitiesControl entitiesControl) {
        for (IEntity entity : adjacentEntities) {
            if (!(entity instanceof CharacterEntity)) {
                entitiesControl.removeEntity(entity);
            }
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

    @Override
    protected void activate(EntitiesControl entitiesControl) {
        if (this.isArmed) {
            explode(entitiesControl);
        }
    }

    @Override
    protected void deactivate() {
        // Bombs should do nothing when deactivated
    }
}
