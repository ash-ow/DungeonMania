package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.collectableEntities.buildableEntities.BowEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.collectableEntities.buildableEntities.*;
import dungeonmania.entities.collectableEntities.*;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private EntitiesControl inventory = new EntitiesControl();

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
    public boolean isPassable() {
        return false;
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
        if(this.containedInInventory("armour")) {
            //ArmourEntity armour = findFirstInInventory("armour");
        }
        this.health -= ((enemyHealth * enemyDamage) / 10);
    }
//endregion

//region Inventory
    public void addEntityToInventory(IEntity entity) {
        inventory.addEntities(entity);
    }

    public EntitiesControl getInventory() {
        return inventory;
    }

    public void removeEntityFromInventory(IEntity entity) {
        inventory.removeEntity(entity);
    }

    public List<ItemResponse> getInventoryInfo() {
        List<ItemResponse> info = new ArrayList<ItemResponse>();
        for (IEntity entity : inventory.getEntities()) {
            info.add(new ItemResponse(entity.getId(), entity.getType()));
        }
        return info;
    }

    public boolean containedInInventory(String type) {
        for (IEntity entity: inventory.getEntities()) {
            if(entity.getType() == type) {
                return true;
            }
        }
        return false;
    }

    // intention here is that this will be a helper function which will find and return the first instance of an entity
    public IEntity findFirstInInventory(String type) {
        for (IEntity entity: inventory.getEntities()) {
            if(entity.getType() == type) {
                return entity;
            }
        }
        return null;
    }


//endregion

    @Override
    public void move(Direction direction, EntitiesControl entitiesControl) {
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.entitiesFromPosition(target);
        List <IInteractingEntity> targetInteractable = entitiesControl.entitiesInteractableInRange(targetEntities);
        boolean interacted = false;
        for (IInteractingEntity entity : targetInteractable) { // Slight bug if player interacts with many things stacked on top of each other- keeps moving
            if (entity.interactWithPlayer(entitiesControl, direction, this)) {
                interacted = true;
                this.move(direction);
            }
        }
        if ((targetEntities.size() == 0) || (!EntitiesControl.entitiesUnpassable(targetEntities) && !interacted)) {
            this.move(direction);
        }
    }

// region Build
    public void build(String buildable) {
        List<ICollectableEntity> playerInventory = this.getInventory().entitiesFromCollectables();
        if (buildable == "bow") {
            BowEntity bow = new BowEntity();
            if (bow.isBuildable(playerInventory)) {
                this.addEntityToInventory(bow);
                removeBuildMaterials("wood", 1);
                removeBuildMaterials("arrow", 3);
            }
        } else if (buildable == "shield") {
            ShieldEntity shield = new ShieldEntity();
            if (shield.isBuildable(playerInventory)) {
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
        while(removed < amount) {
            for(IEntity material : this.getInventory().entitiesOfSameType(type)) {
                //Might have to go through the entity itself so that there aren't empty functions
                removeEntityFromInventory(material);
                removed++;
            }
        }
    }

    public List<String> getBuildableList() {
        List<ICollectableEntity> playerInventory = this.getInventory().entitiesFromCollectables();
        List<String> buildable = new ArrayList<>();
        BowEntity bow = new BowEntity();
        if (bow.isBuildable(playerInventory)) {
            buildable.add(bow.getType());
        }
        ShieldEntity shield = new ShieldEntity();
        if (shield.isBuildable(playerInventory)) {
            buildable.add(shield.getType());
        }
        return buildable;
    }
//endregion 
}
