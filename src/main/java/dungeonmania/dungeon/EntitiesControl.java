package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonParser;
import dungeonmania.util.Position;
import dungeonmania.util.RandomChance;
import dungeonmania.dungeon.entitiesFactory.EntitiesFactory;
import dungeonmania.entities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.moveBehaviour.RunAway;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.*;

public class EntitiesControl {
    private List<IEntity> entities;
    private Random rand = new Random();
    private Integer tickCounter = 1;
    private Integer entityCounter = 0;
    public final static HashMap<GameModeType, Double> difficulty;
    private Position playerStartPosition = new Position(0, 0);
    static {
        difficulty = new HashMap<>();
        difficulty.put(GameModeType.HARD, 20.0/15.0);
        difficulty.put(GameModeType.PEACEFUL, 15.0/20.0);
        difficulty.put(GameModeType.STANDARD, 1.0);
    }
    public final static List<EntityTypes> usableItems;
    static {
        usableItems = new ArrayList<>();
        usableItems.add(EntityTypes.HEALTH_POTION);
        usableItems.add(EntityTypes.INVINCIBILITY_POTION);
        usableItems.add(EntityTypes.BOMB);
        usableItems.add(EntityTypes.INVISIBILITY_POTION);
    }

    public void setPlayerStartPosition(Position playerStartPosition) {
        this.playerStartPosition = playerStartPosition;
    }

    public EntitiesControl() {
        entities = new ArrayList<IEntity>();
    }

    public void addEntity(IEntity entity) {
        entities.add(entity);
        Integer layer = this.getLayerFromPosition(entity.getPosition());
        entity.setPosition(entity.getPosition().asLayer(layer));
        entityCounter++;
    }
    
    /**
     * Creates a new entity on the map
     * @param entity  entity to be created
     */
    public void createNewEntityOnMap(IEntity entity) {
        entity.setId(Integer.toString(entityCounter));
        entities.add(entity);
        Integer layer = this.getLayerFromPosition(entity.getPosition());
        entity.setPosition(entity.getPosition().asLayer(layer));
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
            entity.setMoveBehvaiour(new RunAway());
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

    private Integer getLayerFromPosition(Position position) {
        return this.getAllEntitiesFromPosition(position).size();
    }

    public static boolean containsBlockingEntities(List<IEntity> entityList) {
        return entityList.stream().filter(IBlocker.class::isInstance).map(IBlocker.class::cast).anyMatch(IBlocker::isBlocking);
    }

    public static <T extends IEntity> IEntity getFirstEntityOfType(List<T> entityList, Class<?> cls) {
        return entityList.stream().filter(entity -> entity.getClass().equals(cls)).findFirst().orElse(null);
    }

    public <T extends IEntity> IEntity getFirstEntityOfType(Class<?> cls) {
        return getFirstEntityOfType(this.entities, cls);
    }

    public boolean positionContainsEntityType(Position position, Class<?> cls) {
        List<IEntity> entityList =  this.getAllEntitiesFromPosition(position);
        
        if (getFirstEntityOfType(entityList, cls) != null) {
            return true;
        }
        return false;
    }
    // endregion
    
    
    public void createEntity(DungeonEntityJsonParser jsonInfo, GameModeType gameMode) {
        EntitiesFactory.generateEntity(jsonInfo, this, gameMode);
    }
    
    /**
     * Creates an entity
     * @param x         x-coordinate on the map
     * @param y         y-coordinate on the map
     * @param type      type of entity
     */
    public void createEntity(Integer x, Integer y, EntityTypes type) {
        DungeonEntityJsonParser jsonElement = new DungeonEntityJsonParser();
        jsonElement.setPosition(new Position(x, y));
        jsonElement.setType(type);
        EntitiesFactory.generateEntity(jsonElement, this, GameModeType.STANDARD);
    }

    public Position getLargestCoordinate() {
        int x = 1, y = 1;
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
    public void generateEnemyEntities(GameModeType gameMode) {
        generateSpider(gameMode);
        generateZombieToast(gameMode);
        generateMercenary(gameMode);
        tickCounter++;
    }

    /**
     * Generates mercenaries based on game mode
     * @param gameMode         difficulty of the game
     */
    private void generateMercenary(GameModeType gameMode) {
        if (tickCounter % (int) Math.ceil(30 / difficulty.get(gameMode)) == 0) {
            List<IMovingEntity> enemy = getEntitiesOfType(IMovingEntity.class);
            if (enemy.size() > 0) {
                this.createEntity(playerStartPosition.getX(), playerStartPosition.getY(), EntityTypes.MERCENARY);
            }
        }
    }

    /**
     * Generates spider based on game mode
     * @param gameMode         difficulty of the game
     */
    private void generateSpider(GameModeType gameMode) {
        // TODO replace this with an enemy generator
        List<SpiderEntity> spiders = this.getAllEntitiesOfType(SpiderEntity.class);
        if (spiders.size() < 4) {
            Position largestCoordinate = this.getLargestCoordinate();
            int largestX = largestCoordinate.getX();
            int largestY = largestCoordinate.getY();
            int randomX = rand.nextInt(largestX);
            int randomY = rand.nextInt(largestY);
            if (RandomChance.getRandomBoolean((float) (.05f * difficulty.get(gameMode)))
                && !this.positionContainsEntityType(new Position(randomX, randomY), BoulderEntity.class)) {
                this.createEntity(randomX, randomY, EntityTypes.SPIDER);
            }
        }
    }

    /**
     * Generates zombie toast based on game mode
     * @param gameMode         difficulty of the game
     */
    private void generateZombieToast(GameModeType gameMode) {
        if (tickCounter % (int) Math.ceil(20 / difficulty.get(gameMode)) == 0) {
            List<ZombieToastSpawnerEntity> spawnerEntities = getEntitiesOfType(ZombieToastSpawnerEntity.class);
            for (ZombieToastSpawnerEntity spawner : spawnerEntities) {
                this.createEntity(
                    spawner.getPosition().getX(), 
                    spawner.getPosition().getY(), 
                    EntityTypes.ZOMBIE_TOAST
                );
            }
        }
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
}
