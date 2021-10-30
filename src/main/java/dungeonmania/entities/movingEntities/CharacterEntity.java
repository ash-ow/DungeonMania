package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private EntitiesControl inventory = new EntitiesControl();
    private Position previousPosition;
    public List<IBattlingEntity> teammates = new ArrayList<>();

    public CharacterEntity() {
        this(0, 0, 0);
    }
    
    public CharacterEntity(int x, int y, int layer) {
        super(x, y, layer, "player");
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
//endregion

    public void move(Direction direction, EntitiesControl entitiesControl) {
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        List<IContactingEntity> targetInteractable = entitiesControl.entitiesInteractableInRange(targetEntities);
        boolean interacted = false;
        for (IContactingEntity entity : targetInteractable) {
            // TODO fix bug where player interacts with many things stacked on top of each other and keeps moving
            if (entityIsNotAnArmedBomb(entity)) {
                interacted = interact(entity, entitiesControl, direction);
            }
        }
        if (
            targetLocationIsEmpty(targetEntities) ||
            (!EntitiesControl.containsUnpassableEntities(targetEntities) && !interacted)) {
            previousPosition = this.position;
            this.move(direction);
        }
    }

    private boolean entityIsNotAnArmedBomb(IContactingEntity entity) {
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

    private boolean interact(IContactingEntity entity, EntitiesControl entitiesControl, Direction direction) {
        if (entity.contactWithPlayer(entitiesControl, direction, this)) {
            Position target = this.position.translateBy(direction);
            List<IEntity> newTargetEntities = entitiesControl.getAllEntitiesFromPosition(target);
            if (!EntitiesControl.containsUnpassableEntities(newTargetEntities)) {
                previousPosition = this.position;
                this.move(direction);
            }
            return true;
        }
        return false;
    }
    
    public void useItem(String type, EntitiesControl entitiesControl) {
        for (IEntity ent : this.inventory.getEntities()) {
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

    public Position getPreviousPosition() {
        return this.previousPosition;
    }
}
