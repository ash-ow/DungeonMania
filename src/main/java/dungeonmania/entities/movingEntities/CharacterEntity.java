package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private EntitiesControl inventory = new EntitiesControl();

    public CharacterEntity() {
        this(0, 0, 0);
    }
    
    public CharacterEntity(int x, int y, int layer) {
        super(x, y, layer, "player");
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

    public int getDamage() {
        // TODO determine correct Character damage
        return 3;
    }

    @Override
    public void loseHealth(float enemyHealth, int enemyDamage) {
        this.health -= ((enemyHealth * enemyDamage) / 10);
    }
//endregion

//region Inventory
    public void addEntityToInventory(IEntity entity) {
        inventory.addEntities(entity);
    }

    public EntitiesControl getInventory() {
        return this.inventory;
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

//region Moving
    public void move(Direction direction, EntitiesControl entitiesControl) {
        Position target = position.translateBy(direction);
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(target);
        if ( !EntitiesControl.containsBlockingEntities(targetEntities) ) {
            this.move(direction);
        } else {
            tryUnblockAndMove(targetEntities, direction, entitiesControl);
        }
    }

    private void tryUnblockAndMove(List<IEntity> targetEntities, Direction direction, EntitiesControl entitiesControl) {
        List<IBlocker> targetBlockers = EntitiesControl.getEntitiesOfType(targetEntities, IBlocker.class);
        boolean targetIsUnblocked = true;
        for (IBlocker blocker : targetBlockers) {
            // TODO fix bug where player interacts with many things stacked on top of each other and keeps moving
            targetIsUnblocked = blocker.tryMove(this, direction, entitiesControl);
        }
        if ( targetIsUnblocked ) {
            this.move(direction);
        }
    }

    boolean targetLocationIsEmpty(List<IEntity> targetEntities) {
        return targetEntities.isEmpty();
    }
//endregion

    private boolean interact(IInteractingEntity entity, EntitiesControl entitiesControl, Direction direction) {
        if (entity.interactWithPlayer(entitiesControl, direction, this)) {
            Position target = this.position.translateBy(direction);
            List<IEntity> newTargetEntities = entitiesControl.getAllEntitiesFromPosition(target);
            if (!EntitiesControl.containsBlockingEntities(newTargetEntities)) {
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
}
