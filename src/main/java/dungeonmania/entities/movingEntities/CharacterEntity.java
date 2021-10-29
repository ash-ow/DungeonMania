package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.*;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    // private EntitiesControl inventory = new EntitiesControl();
    private List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();

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

    public int damage = 0;

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
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
//endregion

//region Inventory
    public void addEntityToInventory(ICollectableEntity entity) {
        inventory.add(entity);
    }

    public List<ICollectableEntity> getInventory() {
        return inventory;
    }

    public void removeEntityFromInventory(ICollectableEntity entity) {
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

    @Override
    public void move(Direction direction, EntitiesControl entitiesControl) {
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        List<IInteractingEntity> targetInteractable = entitiesControl.entitiesInteractableInRange(targetEntities);
        boolean interacted = false;
        for (IInteractingEntity entity : targetInteractable) {
            // TODO fix bug where player interacts with many things stacked on top of each other and keeps moving
            if (entityIsNotAnArmedBomb(entity)) {
                interacted = interact(entity, entitiesControl, direction);
            }
        }
        if (
            targetLocationIsEmpty(targetEntities) ||
            (!EntitiesControl.containsUnpassableEntities(targetEntities) && !interacted)) {
            this.move(direction);
        }
    }

// region Build
    public void build(String buildable) {
        if (buildable == "bow") {
            BowEntity bow = new BowEntity();
            if (bow.isBuildable(this.inventory)) {
                this.addEntityToInventory(bow);
                removeBuildMaterials("wood", 1);
                removeBuildMaterials("arrow", 3);
            }
        } else if (buildable == "shield") {
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
    private boolean entityIsNotAnArmedBomb(IInteractingEntity entity) {
        // TODO stop interacting with entities before you determine if you can move into that space. Once you've worked that out, remove this method
        if (entity instanceof BombEntity) {
            BombEntity bomb = (BombEntity)entity;
            return !bomb.isArmed();
        }
        return true;
    }

    boolean targetLocationIsEmpty(List<IEntity> targetEntities) {
        return targetEntities.size() == 0;
    }

    private boolean interact(IInteractingEntity entity, EntitiesControl entitiesControl, Direction direction) {
        if (entity.interactWithPlayer(entitiesControl, direction, this)) {
            Position target = this.position.translateBy(direction);
            List<IEntity> newTargetEntities = entitiesControl.getAllEntitiesFromPosition(target);
            if (!EntitiesControl.containsUnpassableEntities(newTargetEntities)) {
                this.move(direction);
            }
            return true;
        }
        return false;
    }
    
    public void useItem(String type, EntitiesControl entitiesControl) {
        for (ICollectableEntity ent : this.inventory) {
            if (ent instanceof ICollectableEntity) {
                ICollectableEntity item = (ICollectableEntity)ent;
                if (item.getType().equals(type)) {
                    this.useItemCore(item, entitiesControl);
                    return;
                }
            } else {
                // TODO re-implement inventory so it just contains ICollectableEntities. You can base it off of the EntityControl
            }
        }
    }

    private void useItemCore(ICollectableEntity item, EntitiesControl entitiesControl) {
        // TODO create an ItemType class with constant strings
        // TODO decrement the amount of this item in the inventory
        System.out.println("using " + type);
        item.used(this);
        if (item.isPlacedAfterUsing()) {
            item.setPosition(this.getPosition());
            entitiesControl.addEntities(item);
        }
    }
}
