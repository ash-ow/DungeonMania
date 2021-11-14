package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.EntityTypes;

import dungeonmania.util.Position;
import dungeonmania.dungeon.entitiesFactory.EntitiesFactory;
import dungeonmania.entities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.moveBehaviour.RunAway;
import dungeonmania.generators.EnemyEntityGenerator;

public class EntitiesControl {
    private List<IEntity> entities;
    private Integer tickCounter = 1;
    private Integer entityCounter = 0;
    public static final List<EntityTypes> usableItems;
    static {
        usableItems = new ArrayList<>();
        usableItems.add(EntityTypes.HEALTH_POTION);
        usableItems.add(EntityTypes.INVINCIBILITY_POTION);
        usableItems.add(EntityTypes.BOMB);
        usableItems.add(EntityTypes.INVISIBILITY_POTION);
        usableItems.add(EntityTypes.SCEPTRE);
    }

    public EntitiesControl() {
        entities = new ArrayList<IEntity>();
    }

    public void addEntity(IEntity entity) {
        entities.add(entity);
        entityCounter++;
    }
    
    /**
     * Creates a new entity on the map
     * @param entity  entity to be created
     */
    public void createNewEntityOnMap(IEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity parameter cannot be null.");
        }

        entity.setId(Integer.toString(entityCounter));
        entities.add(entity);
        entityCounter++;
    }

    /**
     * Removes the entity
     * @param entity entity to be removed
     */
    public void removeEntity(IEntity entity) {
        entities.remove(entity);
    }

    public List<IEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<IEntity> entities) {
        this.entities = entities;
    }

    /**
     * Calls tick function for all ticking entities
     */
    public void tick() {
        List<ITicker> tickers = getAllTickingEntities();
        for (ITicker ticker : tickers) {
            ticker.tick(this);
        }
    }

    public List<ITicker> getAllTickingEntities() {
        return this.entities.stream()
            .filter(ITicker.class::isInstance)
            .map(ITicker.class::cast)
            .collect(Collectors.toList());
    }

    /**
     * Activates all logic entities based on their switches
     */
    public void checkLogicEntities() {
        List<LogicEntity> logicEnt = getAllLogicEntities();
        for (LogicEntity le : logicEnt) {
            le.checkLogic(this);
        }
    }

    public List<LogicEntity> getAllLogicEntities() {
        return this.entities.stream()
            .filter(LogicEntity.class::isInstance)
            .map(LogicEntity.class::cast)
            .collect(Collectors.toList());
    }

    /**
     * Moves all moving entities based on player
     * @param player    player entity which is used to determine move behaviour
     */
    public void moveAllMovingEntities(CharacterEntity player) {
        List<IAutoMovingEntity> movingEntities = getAllAutoMovingEntities();
        for (IAutoMovingEntity entity : movingEntities) {
            entity.move(this, player);
        }
    }

    /**
     * Moves all moving entities away based on player
     * @param player    player entity which is used to determine move behaviour
     */
    public void runAwayAllMovingEntities(CharacterEntity player) {
        List<IAutoMovingEntity> movingEntities = getAllAutoMovingEntities();
        for (IAutoMovingEntity entity : movingEntities) {
            entity.setMoveBehaviour(new RunAway());
        }
    }

// region Filter
    public List<IAutoMovingEntity> getAllAutoMovingEntities() {
        return EntitiesControl.getEntitiesOfType(this.entities, IAutoMovingEntity.class);
    }

    public List<IContactingEntity> getInteractableEntitiesFrom(List<IEntity> entityList) {
        return EntitiesControl.getEntitiesOfType(entityList, IContactingEntity.class);
    }

    public static <T> List<T> getEntitiesOfType(List<?> entityList, Class<T> cls) {
        return entityList.stream().filter(cls::isInstance).map(cls::cast).collect(Collectors.toList());
    }

    public <T extends IEntity> List<T> getAllEntitiesOfType(Class<T> cls) {
        return EntitiesControl.getEntitiesOfType(this.entities, cls);
    }

    public <T extends IEntity> List<T> getEntitiesOfType(Class<T> cls) {
        return getEntitiesOfType(this.entities, cls);
    }

    public List<IEntity> getAllEntitiesFromPosition(Position position) {
        return this.entities.stream().filter(entity -> entity != null && entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    public Integer getLayerFromPosition(Position position) {
        return this.getAllEntitiesFromPosition(position).size();
    }

    public static boolean containsBlockingEntities(List<IEntity> entityList) {
        return entityList.stream().filter(IBlocker.class::isInstance).map(IBlocker.class::cast).anyMatch(IBlocker::isBlocking);
    }

    public static <T extends IEntity> IEntity getFirstEntityOfType(List<T> entityList, Class<?> cls) {
        return entityList.stream().filter(entity -> entity.getClass().equals(cls)).findFirst().orElse(null);
    }

    public <T extends IEntity> IEntity getFirstEntityOfType(Class<T> cls) {
        return getFirstEntityOfType(this.entities, cls);
    }

    public <T extends IEntity> boolean positionContainsEntityType(Position position, Class<T> cls) {
        List<IEntity> entityList =  this.getAllEntitiesFromPosition(position);
        return getFirstEntityOfType(entityList, cls) != null;
    }
    // endregion
    
    
    public void createEntity(JsonObject jsonInfo) {
        EntitiesFactory.generateEntity(jsonInfo, this);
    }
    
    /**
     * Creates an entity
     * @param x         x-coordinate on the map
     * @param y         y-coordinate on the map
     * @param type      type of entity
     */
    public void createEntity(Integer x, Integer y, EntityTypes type) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", x);
        jsonObject.addProperty("y", y);
        jsonObject.addProperty("type", type.toString());
        EntitiesFactory.generateEntity(jsonObject, this);
    }

    public Position getLargestCoordinate() {
        int x = 1;
        int y = 1;
        for (IEntity entity : entities) {
            if (entity.getPosition().getX() > x) {
                x = entity.getPosition().getX();
            }
            if (entity.getPosition().getX() > y) {
                y = entity.getPosition().getX();
            }
        }
        return new Position(x, y);
    }

    /**
     * Generates enemies based on game mode
     * @param gameMode         difficulty of the game
     */
    public void generateEnemyEntities(GameModeType gameMode, Position playerStartPosition) {
        EnemyEntityGenerator.generateEnemyEntities(this, this.tickCounter, gameMode, playerStartPosition);
        tickCounter++;
    }

    /**
     * Finds all adjacent entities to a position
     * @param position         position to be calculated from
     * @returns list of all adjacent entities
     */
    public List<IEntity> getAllAdjacentEntities(Position position) {
        List<IEntity> adjacentEntities = new ArrayList<IEntity>();
        for (IEntity ent : this.entities) {
            Position entPosition = ent.getPosition();
            if (position.getCardinallyAdjacentPositions().contains(entPosition)) {
                adjacentEntities.add(ent);
            }
        }
        return adjacentEntities;
    }

    public List<IEntity> getBombAdjacent(Position position) {
        List<IEntity> adjacentEntities = new ArrayList<IEntity>();
        for (IEntity ent : this.entities) {
            Position entPosition = ent.getPosition();
            if (position.getAdjacentPositions().contains(entPosition)) {
                adjacentEntities.add(ent);
            }
        }
        return adjacentEntities;
    }

    public boolean contains(Entity entity) {
        return this.entities.stream().anyMatch(ent -> ent.equals(entity));
    }
    
    public IEntity getEntityById(String id) {
        return this.entities.stream()
            .filter(entity -> entity.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public static <T extends IEntity> IEntity getEntityById(List<T> entityList, String id) {
        return entityList.stream()
            .filter(entity -> entity.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public void moveMercenariesAfterAttack(CharacterEntity player) {
        List<MercenaryEntity> movingEntities = getAllEntitiesOfType(MercenaryEntity.class);
        for (MercenaryEntity merc : movingEntities) {
            merc.move(this, player);
        }
    }
}
