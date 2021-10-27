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

    // Build
    public void build(String buildable) {
        List<ICollectableEntity> playerInventory = this.getInventory().entitiesFromCollectables();
        if (buildable == "bow") {
            BowEntity bow = new BowEntity(0,0,0);
            if (bow.isBuildable(playerInventory)) {
                this.addEntityToInventory(bow);
                List<IEntity> woodInInventory = this.getInventory().entitiesOfSameType("wood"); 
                int woodRemoved = 0;
                while (woodRemoved < 1) {
                    for (IEntity wood : woodInInventory) {
                        removeEntityFromInventory(wood);
                        woodRemoved++;
                    }
                }
                List<IEntity> arrowsInInventory = this.getInventory().entitiesOfSameType("arrow"); 
                int arrowsRemoved = 0;
                while (arrowsRemoved < 3) {
                    for (IEntity arrow : arrowsInInventory) {
                        removeEntityFromInventory(arrow);
                        arrowsRemoved++;
                    }
                }
            }
        }
    }
}
