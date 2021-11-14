package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IBlocker;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.collectableEntities.IDefensiveEntity;
import dungeonmania.entities.collectableEntities.buildableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.generators.EnemyEntityGenerator;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;

import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.*;

public class CharacterEntity extends Entity implements IMovingEntity, IBattlingEntity {
    private Inventory inventory = new Inventory();
    private Position previousPosition;
    public List<IBattlingEntity> teammates = new ArrayList<>();
    private int invincibilityRemaining = 0;
    private int invisibilityRemaining = 0;
    private GameModeType gameMode;
    private boolean isTimeTravelling = false;
    private List<ITicker> activeItems = new ArrayList<>();

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
    
    public CharacterEntity(int x, int y, EntityTypes type, GameModeType gameMode) {
        super(x, y, type);
        this.previousPosition = new Position(x, y);
        this.gameMode = gameMode;
        this.health = (int) Math.ceil(100 / EnemyEntityGenerator.difficulty.get(gameMode));
    }

    /**
     * Character constructor
     * @param x          x-coordinate on the map
     * @param y          y-coordinate on the map
     * @param gameMode   denotes the difficulty settings of the game 
     */
    public CharacterEntity(int x, int y, GameModeType gameMode) {
        this(x, y, EntityTypes.PLAYER, gameMode);      
        this.previousPosition = new Position(x, y);
        this.gameMode = gameMode;
        this.health = (int) Math.ceil(100 / EnemyEntityGenerator.difficulty.get(gameMode));
    }

    public CharacterEntity(JsonObject info, GameModeType gameMode) {
        this(info.get("x").getAsInt(), info.get("y").getAsInt(), gameMode);
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

    @Override
    public float getDamage() {
        return (float) (3 / EnemyEntityGenerator.difficulty.get(this.gameMode));
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
            for (ICollectable item : this.inventory.getItemsFromInventoryOfType(IDefensiveEntity.class)) {
                IDefensiveEntity defender = (IDefensiveEntity)item;
                damage = defender.reduceDamage(damage, this);
            }
            this.health -= damage;
        }
        return damage;
    }

    /**
     * Adds team mate to the list of teammates
     * @param teamMember a battling entity which will be added to the list of team mates   
     */
    public void addTeammates(IBattlingEntity teamMember) {
        if (!teammates.contains(teamMember)) {
            teammates.add(teamMember);
        }
    }

    public void removeTeammates(IBattlingEntity teamMember) {
        if (teammates.contains(teamMember)) {
            teammates.remove(teamMember);
        }
    }

    /**
     * Checks whether the character is alive. If they are not, and they have a ring, they will use the ring
     */
    @Override
    public boolean isAlive() {
        if (this.getHealth() <= 0) {
            OneRingEntity ring = this.inventory.getFirstItemOfType(OneRingEntity.class);
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

    public Inventory getInventory() {
        return this.inventory;
    }

    public List<ICollectable> getInventoryItems() {
        return this.inventory.getItems();
    }

    public List<ItemResponse> getInventoryInfo() {
        List<ItemResponse> info = new ArrayList<ItemResponse>();
        for (ICollectable entity : this.getInventoryItems()) {
            info.add(new ItemResponse(entity.getId(), entity.getType()));
        }
        return info;
    }

    /**
     * Finds the first instance of an item type in the inventory
     * @param type the type of item being searched for
     */
    public ICollectable findFirstInInventory(EntityTypes type) {
        for (ICollectable entity: this.getInventoryItems()) {
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
        if (gameMode.equals(GameModeType.HARD)) {
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
        if (!EntitiesControl.containsBlockingEntities(targetEntities) || canUnblock(targetEntities, direction, entitiesControl) ) {
            this.previousPosition = this.position;
            this.move(direction); 
            decrementPotionDurations();    
            interactWithAll(targetEntities, entitiesControl);
        }
        this.tickActiveItems(entitiesControl);
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
                    bow.build(this.inventory);
                } else {
                    throw new InvalidActionException(itemToBuild.toString());
                }
                break;
            case SHIELD:
                ShieldEntity shield = new ShieldEntity();
                if (shield.isBuildable(this.inventory)) {
                    shield.build(this.inventory);
                } else {
                    throw new InvalidActionException(itemToBuild.toString());
                }
                break;
            case SCEPTRE:
                SceptreEntity sceptre = new SceptreEntity();
                if (sceptre.isBuildable(this.inventory)) {
                    sceptre.build(this.inventory);
                } else {
                    throw new InvalidActionException(itemToBuild.toString());
                }
                break;
            case MIDNIGHT_ARMOUR:
                MidnightArmourEntity midnightArmour = new MidnightArmourEntity();
                if (midnightArmour.isBuildable(this.inventory)) {
                    midnightArmour.build(this.inventory);
                } else {
                    throw new InvalidActionException(itemToBuild.toString());
                }
                break;
            default:
                throw new InvalidActionException(itemToBuild.toString());
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
    protected void interactWithAll(List<IEntity> targetEntities, EntitiesControl entitiesControl) {
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
        IEntity entity = this.getInventory().getInventoryItemById(itemID);
        if (entity == null) {
            throw new InvalidActionException(itemID + "not in inventory");
        } else if (!EntitiesControl.usableItems.contains(entity.getType())) {
            throw new IllegalArgumentException();
        }
        for (ICollectable item : this.getInventoryItems()){
            if (item.getId().equals(itemID)) {
                this.useItemCore(item, entitiesControl);
                break;
            }
        }
        this.tickActiveItems(entitiesControl);
    }

    /**
     * Uses an item 
     * @param item             Item to be used
     * @param entitiesControl  list of all entities
     */
    private void useItemCore(ICollectable item, EntitiesControl entitiesControl) {
        item.used(this);
        if (item.isPlacedAfterUsing()) {
            item.setPosition(this.getPosition());
            entitiesControl.addEntity(item);
        }
    }

    private void tickActiveItems(EntitiesControl entitiesControl) {
        this.activeItems.stream().forEach(a -> a.tick(entitiesControl));
    }

    public void addActiveItem(ITicker item) {
        this.activeItems.add(item);
    }

    public Position getPreviousPosition() {
        return this.previousPosition;
    }

    public boolean IsTimeTravelling() {
        return this.isTimeTravelling;
    }
    
    public void setTimeTravelling(boolean timeTravel) {
        this.isTimeTravelling = timeTravel;
    }


}
