package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
// import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.*;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private List<ICollectableEntity> inventory = new ArrayList<>();
    private Position previousPosition;
    public List<IBattlingEntity> teammates = new ArrayList<>();
    private boolean isInvincible;

    public CharacterEntity() {
        this(0, 0, 0);
    }
    
    public CharacterEntity(int x, int y, int layer) {
        super(x, y, layer, "player");
        this.previousPosition = new Position(x, y);
    }

    @Override
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

    public void loseHealth(float enemyHealth, int enemyDamage) {
        float damage = ((enemyHealth * enemyDamage) / 10);
        if(this.containedInInventory("armour")) {
            ArmourEntity armour = (ArmourEntity) findFirstInInventory("armour");
            damage = armour.reduceDamage(damage, this);
        }
        if(this.containedInInventory("shield")) {
            ShieldEntity shield = (ShieldEntity) findFirstInInventory("shield");
            damage = shield.reduceDamage(damage, this);
        }
        this.health -= damage;
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

    public boolean containedInInventory(String type) {
        for (ICollectableEntity entity: inventory) {
            if(entity.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public ICollectableEntity findFirstInInventory(String type) {
        for (ICollectableEntity entity: inventory) {
            if(entity.getType() == type) {
                return entity;
            }
        }
        return null;
    }

    public ICollectableEntity findCollectableById(String id) {
        for (ICollectableEntity entity: inventory) {
            if(entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }


//endregion

//region Moving
    public void move(Direction direction, EntitiesControl entitiesControl) {
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) || canUnblock(targetEntities, direction, entitiesControl) ) {
            this.previousPosition = this.position;
            this.move(direction);            
            interactWithAll(targetEntities, entitiesControl);
        }
    }

// region Build
    public void build(String buildable) {
        if (buildable.equals("bow")) {
            BowEntity bow = new BowEntity();
            if (bow.isBuildable(this.inventory)) {
                this.addEntityToInventory(bow);
                removeBuildMaterials("wood", 1);
                removeBuildMaterials("arrow", 3);
            }
        } else if (buildable.equals("shield")){
            ShieldEntity shield = new ShieldEntity();
            if (shield.isBuildable(this.inventory)) {
                this.addEntityToInventory(shield);
                removeBuildMaterials("wood", 2);
                if(this.containedInInventory("treasure")) {
                    removeBuildMaterials("treasure", 1);
                } else if (this.containedInInventory("key")) {
                    removeBuildMaterials("key", 1);
                }
            }
        }
    }

    public void removeBuildMaterials(String type, int amount) {
        int removed = 0;
        List<ICollectableEntity> toRemove = new ArrayList<>();
        while(removed < amount) {
            for(ICollectableEntity material : this.inventory) {
                //Might have to go through the entity itself so that there aren't empty functions
                if (material.getType() == type) {
                    toRemove.add(material);
                    removed++;
                }
            }
        }
        for (ICollectableEntity material : toRemove) {
            removeEntityFromInventory(material);
        }
    }

    public List<String> getBuildableList() {
        List<String> buildable = new ArrayList<>();
        BowEntity bow = new BowEntity();
        if (bow.isBuildable(this.inventory)) {
            buildable.add(bow.getType());
        }
        ShieldEntity shield = new ShieldEntity();
        if (shield.isBuildable(this.inventory)) {
            buildable.add(shield.getType());
        }
        return buildable;
    }
//endregion    
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
