package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private List<ICollectableEntity> inventory = new ArrayList<>();
    private Position previousPosition = new Position(0, 0);
    public List<IBattlingEntity> teammates = new ArrayList<>();
    private int invincibilityRemaining = 0;
    private int invisRemaining = 0;

    public CharacterEntity() {
        this(0, 0, 0);
    }
    
    public CharacterEntity(int x, int y, int layer) {
        super(x, y, layer, "player");
    }

    public EntityResponse getInfo() {
        return new EntityResponse(this.getId(), this.getType(), this.getPosition(), false);
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

//region Battling
    private float health = 100;

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    public float getDamage() {
        return 3;
    }


    @Override
    public void loseHealth(float enemyHealth, float enemyDamage) {
        if (!this.isInvincible()) {
            this.health -= ((enemyHealth * enemyDamage) / 10);
        }
    }

    public void addTeammates(IBattlingEntity teamMember) {
        teammates.add(teamMember);
    }

    public IEntity getInventoryItem(String itemID) {
        return inventory.stream().filter(item -> item.getId().equals(itemID)).findFirst().orElse(null);
    }

//endregion

//region Inventory
    public void addEntityToInventory(ICollectableEntity entity) {
        inventory.add(entity);
    }

    public List<ICollectableEntity> getInventory() {
        return this.inventory;
    }

    public void removeEntityFromInventory(IEntity entity) {
        inventory.remove(entity);
    }

    public List<ItemResponse> getInventoryInfo() {
        List<ItemResponse> info = new ArrayList<ItemResponse>();
        for (ICollectableEntity entity : inventory) {
            info.add(new ItemResponse(entity.getId(), entity.getType()));
        }
        return info;
    }
//endregion

//Player Potion Effects region 

    public boolean isInvincible() {
        return this.invincibilityRemaining > 0;
    }

    public void setInvincibilityRemaining(int invincRemaining) {
        this.invincibilityRemaining = invincRemaining;
    }

    public boolean isInvisible() {
        return this.invisRemaining > 0;
    }
    
    public void setInvisible(int invisRemaining) {
        this.invisRemaining = invisRemaining;
    }

//End Region

//region Moving
    public void move(Direction direction, EntitiesControl entitiesControl) {
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) || canUnblock(targetEntities, direction, entitiesControl) ) {
            this.previousPosition = this.position;
            this.move(direction); 
            decrementPotionDurations();    
            interactWithAll(targetEntities, entitiesControl);
        }
    }

    private void decrementPotionDurations() {
        this.invisRemaining--;
        this.invincibilityRemaining--;
    }

    private void interactWithAll(List<IEntity> targetEntities, EntitiesControl entitiesControl) {
        List<IContactingEntity> targetInteractable = entitiesControl.getInteractableEntitiesFrom(targetEntities);
        for (IContactingEntity entity : targetInteractable) {
            entity.contactWithPlayer(entitiesControl, this);
        }
    }

    private boolean canUnblock(List<IEntity> targetEntities, Direction direction, EntitiesControl entitiesControl) {
        List<IBlocker> targetBlockers = EntitiesControl.getEntitiesOfType(targetEntities, IBlocker.class);
        boolean targetIsUnblocked = true;
        for (IBlocker blocker : targetBlockers) {
            targetIsUnblocked = blocker.tryUnblock(this, direction, entitiesControl);
        }
        return targetIsUnblocked;
    }
//endregion
    
    public void useItem(String itemID, EntitiesControl entitiesControl) {
        for (ICollectableEntity item : this.inventory) {
            if (item.getId().equals(itemID)) {
                this.useItemCore(item, entitiesControl);
                return;
            }
        }
    }

    private void useItemCore(ICollectableEntity item, EntitiesControl entitiesControl) {
        // TODO decrement the amount of this item in the inventory
        item.used(this);
        if (item.isPlacedAfterUsing()) {
            item.setPosition(this.getPosition());
            entitiesControl.addEntity(item);
        }
    }

    public Position getPreviousPosition() {
        return this.previousPosition;
    }
}
