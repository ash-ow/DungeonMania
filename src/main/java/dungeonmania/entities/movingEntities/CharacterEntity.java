package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.*;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private List<CollectableEntity> inventory = new ArrayList<>();
    private Position previousPosition;
    public List<IBattlingEntity> teammates = new ArrayList<>();
    private boolean isInvincible;

    public CharacterEntity() {
        this(0, 0, 0);
    }
    
    public CharacterEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.PLAYER);
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

    @Override
    public void loseHealth(float enemyHealth, float enemyDamage) {
        float damage = ((enemyHealth * enemyDamage) / 10);
        if(this.containedInInventory(EntityTypes.ARMOUR)) {
            ArmourEntity armour = (ArmourEntity) findFirstInInventory(EntityTypes.ARMOUR);
            damage = armour.reduceDamage(damage, this);
        }
        if(this.containedInInventory(EntityTypes.SHIELD)) {
            ShieldEntity shield = (ShieldEntity) findFirstInInventory(EntityTypes.SHIELD);
            damage = shield.reduceDamage(damage, this);
        }
        this.health -= damage;
    }

    // @Override
    // public void loseHealth(float enemyHealth, float enemyDamage) {
    //     this.health -= ((enemyHealth * enemyDamage) / 10);
    // }

    public void addTeammates(IBattlingEntity teamMember) {
        teammates.add(teamMember);
    }

    public IEntity getInventoryItem(String itemID) {
        return inventory.stream().filter(item -> item.getId().equals(itemID)).findFirst().orElse(null);
    }

    public boolean getInvincible() {
        return this.isInvincible;
    }

    @Override
    public boolean isAlive() {
        if (this.getHealth() <= 0) {
            OneRingEntity ring = (OneRingEntity) EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class);
            if (ring != null) {
                ring.used(this);
            }
            return false;
        }
        return true;
    }
//endregion

//region Inventory
    public void addEntityToInventory(CollectableEntity entity) {
        inventory.add(entity);
    }

    public List<CollectableEntity> getInventory() {
        return this.inventory;
    }

    public void removeEntityFromInventory(IEntity entity) {
        inventory.remove(entity);
    }

    public List<ItemResponse> getInventoryInfo() {
        List<ItemResponse> info = new ArrayList<ItemResponse>();
        for (CollectableEntity entity : inventory) {
            info.add(new ItemResponse(entity.getId(), entity.getType()));
        }
        return info;
    }

    public boolean containedInInventory(EntityTypes type) {
        for (CollectableEntity entity: inventory) {
            if(entity.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public CollectableEntity findFirstInInventory(EntityTypes type) {
        for (CollectableEntity entity: inventory) {
            if(entity.getType().equals(type)) {
                return entity;
            }
        }
        return null;
    }

    public CollectableEntity findCollectableById(String id) {
        for (CollectableEntity entity: inventory) {
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
    public void build(EntityTypes itemToBuild) {
        if (itemToBuild.equals(EntityTypes.BOW)) {
            BowEntity bow = new BowEntity();
            if (bow.isBuildable(this.inventory)) {
                this.addEntityToInventory(bow);
                removeBuildMaterials(EntityTypes.WOOD, 1);
                removeBuildMaterials(EntityTypes.ARROW, 3);
            }
        } else if (itemToBuild.equals(EntityTypes.SHIELD)){
            ShieldEntity shield = new ShieldEntity();
            if (shield.isBuildable(this.inventory)) {
                this.addEntityToInventory(shield);
                removeBuildMaterials(EntityTypes.WOOD, 2);
                if(this.containedInInventory(EntityTypes.TREASURE)) {
                    removeBuildMaterials(EntityTypes.TREASURE, 1);
                } else if (this.containedInInventory(EntityTypes.KEY)) {
                    removeBuildMaterials(EntityTypes.KEY, 1);
                }
            }
        }
    }

    public void removeBuildMaterials(EntityTypes type, int amount) {
        int removed = 0;
        List<CollectableEntity> toRemove = new ArrayList<>();
        while(removed < amount) {
            for(CollectableEntity material : this.inventory) {
                //Might have to go through the entity itself so that there aren't empty functions
                if (material.getType().equals(type)){
                    toRemove.add(material);
                    removed++;
                }
            }
        }
        for (CollectableEntity material : toRemove) {
            removeEntityFromInventory(material);
        }
    }

    public List<String> getBuildableList() {
        List<EntityTypes> buildable = new ArrayList<>();
        BowEntity bow = new BowEntity();
        if (bow.isBuildable(this.inventory)) {
            buildable.add(EntityTypes.BOW);
        }
        ShieldEntity shield = new ShieldEntity();
        if (shield.isBuildable(this.inventory)) {
            buildable.add(EntityTypes.SHIELD);
        }
        return buildable.stream().map(EntityTypes::toString).collect(Collectors.toList());
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
        for (CollectableEntity item : this.inventory) {
            if (item.getId().equals(itemID)) {
                this.useItemCore(item, entitiesControl);
                return;
            }
        }
    }

    private void useItemCore(CollectableEntity item, EntitiesControl entitiesControl) {
        // TODO create an ItemType class with constant strings
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
