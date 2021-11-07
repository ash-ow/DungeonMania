package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.IDefensiveEntity;
import dungeonmania.entities.collectableEntities.buildableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.generators.Generator;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.DungeonEntityJsonObject;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.*;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private List<CollectableEntity> inventory = new ArrayList<>();
    private Position previousPosition;
    public List<IBattlingEntity> teammates = new ArrayList<>();
    private int invincibilityRemaining = 0;
    private int invisibilityRemaining = 0;
    private GameModeType gameMode;

    /**
     * Character constructor
     */
    public CharacterEntity() {
        this(0, 0);
    }
    
    /**
     * Character constructor
     * @param x     x-coordinate on the map
     * @param y     y-coordinate on the map
     */
    public CharacterEntity(int x, int y) {
        this(x, y, GameModeType.STANDARD);
    }
    
    /**
     * Character constructor
     * @param x          x-coordinate on the map
     * @param y          y-coordinate on the map
     * @param gameMode   denotes the difficulty settings of the game 
     */
    public CharacterEntity(int x, int y, GameModeType gameMode) {
        super(x, y, EntityTypes.PLAYER);
        this.previousPosition = new Position(x, y);
        this.gameMode = gameMode;
        this.health = (int) Math.ceil(100 / Generator.difficulty.get(gameMode));
    }

    public CharacterEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
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

    @Override
    public float getDamage() {
        return (float) (3 / Generator.difficulty.get(this.gameMode));
    }

    /**
     * Determines how much health the character will lose based on enemy stats, and whether the character
     * has armour or shield
     * @param enemyHealth   Health of enemy which is used to calculate total damage   
     * @param enemyDamage   Damage of the enemy which is used to calculate total damage
     */
    @Override
    public float loseHealth(float enemyHealth, float enemyDamage) {
        float damage = 0;
        if (!this.isInvincible()) {
            damage = ((enemyHealth * enemyDamage) / 10);
            for (CollectableEntity item : getItemsFromInventory(IDefensiveEntity.class)) {
                IDefensiveEntity defender = (IDefensiveEntity)item;
                damage = defender.reduceDamage(damage, this);
            }
            this.health -= damage;
        }
        return damage;
    }

    /**
     * Adds team mate to the list of teammates
     * @param teamMemeber a battling entity which will be added to the list of team mates   
     */
    public void addTeammates(IBattlingEntity teamMember) {
        teammates.add(teamMember);
    }

    /**
     * Finds an item in the inventory based on its id
     * @param itemID Item identifier
     */
    public IEntity getInventoryItem(String itemID) {
        return inventory.stream().filter(item -> item.getId().equals(itemID)).findFirst().orElse(null);
    }

    /**
     * Checks whether the character is alive. If they are not, and they have a ring, they will use the ring
     */
    @Override
    public boolean isAlive() {
        if (this.getHealth() <= 0) {
            OneRingEntity ring = (OneRingEntity) EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class);
            if (ring != null) {
                ring.used(this);
                return true;
            }
            return false;
        }
        return true;
    }
    
//endregion

//region Inventory

    List<CollectableEntity> getItemsFromInventory(Class<?> cls) {
        return this.inventory.stream().filter(cls::isInstance).collect(Collectors.toList());
    }

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

    /**
     * Checks whether a type of item is in the inventory
     * @param type the type of item being searched for
     */
    public boolean containedInInventory(EntityTypes type) {
        for (CollectableEntity entity: inventory) {
            if(entity.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the first instance of an item type in the inventory
     * @param type the type of item being searched for
     */
    public CollectableEntity findFirstInInventory(EntityTypes type) {
        for (CollectableEntity entity: inventory) {
            if(entity.getType().equals(type)) {
                return entity;
            }
        }
        return null;
    }
//endregion

//Player Potion Effects region 
    /**
     * Determines whether the character is still invincible based on remaining ticks, and game mode
     * @return true if still invincible
     */
    public boolean isInvincible() {
        if (gameMode.equals("Hard")) {
            return false;
        }
        return this.invincibilityRemaining > 0;
    }

    public void setInvincibilityRemaining(int invincibilityRemaining) {
        this.invincibilityRemaining = invincibilityRemaining;
    }

    public boolean isInvisible() {
        return this.invisibilityRemaining > 0;
    }
    
    public void setInvisiblilityRemaining(int invisibilityRemaining) {
        this.invisibilityRemaining = invisibilityRemaining;
    }

//endregion

//region Moving
    /**
     * Moves the character based on direction
     * @param direction            new direction
     * @param entitiesControl      list of entities
     */
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
        this.invisibilityRemaining--;
        this.invincibilityRemaining--;
    }
//endregion

// region Build
    /**
     * Builds an item based on whether inventory. If item is built, removes the required components
     * @param itemToBuild item to be built
     */
    public void build(EntityTypes itemToBuild) {
        switch (itemToBuild) {
            case BOW:
                BowEntity bow = new BowEntity();
                if (bow.isBuildable(this.inventory)) {
                    this.addEntityToInventory(bow);
                    removeBuildMaterials(EntityTypes.WOOD, 1);
                    removeBuildMaterials(EntityTypes.ARROW, 3);
                } else {
                    throw new InvalidActionException(itemToBuild.toString());
                }
                break;
            case SHIELD:
                ShieldEntity shield = new ShieldEntity();
                if (shield.isBuildable(this.inventory)) {
                    this.addEntityToInventory(shield);
                    removeBuildMaterials(EntityTypes.WOOD, 2);
                    if(this.containedInInventory(EntityTypes.TREASURE)) {
                        removeBuildMaterials(EntityTypes.TREASURE, 1);
                    } else if (this.containedInInventory(EntityTypes.KEY)) {
                        removeBuildMaterials(EntityTypes.KEY, 1);
                    } 
                } else {
                    throw new InvalidActionException(itemToBuild.toString());
                }
                break;
            case SCEPTRE:
                SceptreEntity sceptre = new SceptreEntity();
                if (sceptre.isBuildable(this.inventory)) {
                    this.addEntityToInventory(sceptre);
                    removeBuildMaterials(EntityTypes.SUN_STONE, 1);
                    if(this.containedInInventory(EntityTypes.TREASURE)) {
                        removeBuildMaterials(EntityTypes.TREASURE, 1);
                    } else if (this.containedInInventory(EntityTypes.KEY)) {
                        removeBuildMaterials(EntityTypes.KEY, 1);
                    } 
                    if(this.containedInInventory(EntityTypes.WOOD)) {
                        removeBuildMaterials(EntityTypes.WOOD, 1);
                    } else if (this.containedInInventory(EntityTypes.ARROW)) {
                        removeBuildMaterials(EntityTypes.ARROW, 1);
                    } 
                }
                break;
            case MIDNIGHT_ARMOUR:
                MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
                if (midnightArmour.isBuildable(this.inventory)) {
                    this.addEntityToInventory(midnightArmour);
                    removeBuildMaterials(EntityTypes.SUN_STONE, 1);
                    removeBuildMaterials(EntityTypes.ARMOUR, 1);
                }
                break;
            default:
                throw new InvalidActionException(itemToBuild.toString());
        }
    }

    /**
     * Removes build materials based on their type, and the amount of materials which need to be removed
     * @param type   type of entity to be removed
     * @param amount amount of material that needs to be removed for each type
     */
    public void removeBuildMaterials(EntityTypes type, int amount) {
        int removed = 0;
        List<CollectableEntity> toRemove = new ArrayList<>();
        while(removed < amount) {
            for(CollectableEntity material : this.inventory) {
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
    
    /**
     * Returns the list of items which can be built
     */
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

    /**
     * Interacts with all entities which can be interacted with
     * @param targetEntities   list of entities to be interacted with
     * @param entitiesControl  list of all entities
     */
    private void interactWithAll(List<IEntity> targetEntities, EntitiesControl entitiesControl) {
        List<IContactingEntity> targetInteractable = entitiesControl.getInteractableEntitiesFrom(targetEntities);
        for (IContactingEntity entity : targetInteractable) {
            entity.contactWithPlayer(entitiesControl, this);
        }
    }

    /**
     * Checks if an entity in the new direction can be unblocked
     * @param targetEntities   list of entities to be interacted with
     * @param direction        direction the chatacter is moving
     * @param entitiesControl  list of all entities
     */
    private boolean canUnblock(List<IEntity> targetEntities, Direction direction, EntitiesControl entitiesControl) {
        List<IBlocker> targetBlockers = EntitiesControl.getEntitiesOfType(targetEntities, IBlocker.class);
        boolean targetIsUnblocked = true;
        for (IBlocker blocker : targetBlockers) {
            targetIsUnblocked = blocker.tryUnblock(this, direction, entitiesControl);
        }
        return targetIsUnblocked;
    }
//endregion
    
    /**
     * Uses an item based on its ID
     * @param itemId           Identifier of item to be used
     * @param entitiesControl  list of all entities
     */
    public void useItem(String itemID, EntitiesControl entitiesControl) {
        IEntity entity = EntitiesControl.getEntityById(this.inventory, itemID);
        if (entity == null) {
            throw new InvalidActionException(itemID + "not in inventory");
        } else if (!EntitiesControl.usableItems.contains(entity.getType())) {
            throw new IllegalArgumentException();
        }
        for (CollectableEntity item : this.inventory) {
            if (item.getId().equals(itemID)) {
                this.useItemCore(item, entitiesControl);
                return;
            }
        }
    }

    /**
     * Uses an item 
     * @param item             Item to be used
     * @param entitiesControl  list of all entities
     */
    private void useItemCore(CollectableEntity item, EntitiesControl entitiesControl) {
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
