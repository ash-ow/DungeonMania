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
    private Position previousPosition;
    public List<IBattlingEntity> teammates = new ArrayList<>();
    private int countBattle;
    private int countMove;
    boolean isInvincible = false;
    boolean isInvisible = false;

    public CharacterEntity() {
        this(0, 0, 0);
    }
    
    public CharacterEntity(int x, int y, int layer) {
        super(x, y, layer, "player");
        this.countBattle = 0;
        this.countMove = 10;
        this.isInvincible = false;
        this.isInvisible = false; 
        this.previousPosition = new Position(x, y);
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
        // TODO determine correct Character damage
        return 3;
    }


    @Override
    public void loseHealth(float enemyHealth, float enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 10);
    }

    public void addTeammates(IBattlingEntity teamMember) {
        teammates.add(teamMember);
    }

    public IEntity getInventoryItem(String itemID) {
        return inventory.stream().filter(item -> item.getId().equals(itemID)).findFirst().orElse(null);
    }

    public boolean getInvincible() {
        return this.isInvincible;
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
    
    public int getDuration() {
       return this.getCountMove();
    }


    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean isInvincible) {
        if(isInvincible){
            this.isInvincible = true;
            this.setBattleCount(true);
        }
        else {
            this.isInvincible = false;
        }
    }

    public boolean isInvisible() {
        return isInvisible;
        }
    
    public void setInvisible(boolean isInvisible) {
        if(isInvisible){
            this.isInvisible = true;
            }
            else {
                this.isInvisible = false;
            }
        }
    
    public int getBattleCount() {
        return countBattle;
    }
    //TO DO: recheck how this is implemented 
    public void setBattleCount(boolean reset) {
        if(reset)
        {
            countBattle=0;
        }else 
        {
            countBattle++;
        }      
    }

//End Region

//region Moving
    public void move(Direction direction, EntitiesControl entitiesControl) {
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) || canUnblock(targetEntities, direction, entitiesControl) ) {
            this.previousPosition = this.position;
            this.move(direction); 
            setCountMove(false);           
            interactWithAll(targetEntities, entitiesControl);
        }
    }

    public int getCountMove(){
        return countMove;
    }

    public void setCountMove(boolean decrement) {
        if (decrement){
            countMove--;
        }
        else {
            countMove++;
        }
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
            // TODO fix bug where player interacts with many things stacked on top of each other and keeps moving
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
        // TODO create an ItemType class with constant strings
        // TODO decrement the amount of this item in the inventory
        item.used(this);
        if (item.isPlacedAfterUsing()) {
            item.setPosition(this.getPosition());
            entitiesControl.addEntities(item);
        }
    }

    public Position getPreviousPosition() {
        return this.previousPosition;
    }
}
