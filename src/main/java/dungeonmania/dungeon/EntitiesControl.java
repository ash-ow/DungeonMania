package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.Position;
import dungeonmania.util.RandomChance;
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
    public final static HashMap<String, Double> difficulty;
    private Position playerStartPosition = new Position(0, 0);
    static {
        difficulty = new HashMap<>();
        difficulty.put("hard", 20.0/15.0);
        difficulty.put("peaceful", 15.0/20.0);
        difficulty.put("standard", 1.0);
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
        entityCounter++;
    }
    
    /**
     * Creates a new entity on the map
     * @param entity  entity to be created
     */
    private void createNewEntityOnMap(IEntity entity) {
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

    public static boolean containsBlockingEntities(List<IEntity> entityList) {
        return entityList.stream().filter(IBlocker.class::isInstance).map(IBlocker.class::cast).anyMatch(IBlocker::isBlocking);
    }

    public static <T extends IEntity> IEntity getFirstEntityOfType(List<T> entityList, Class<?> cls) {
        return entityList.stream().filter(entity -> entity.getClass().equals(cls)).findFirst().orElse(null);
    }

    public boolean positionContainsEntityType(Position position, Class<?> cls) {
        List<IEntity> entityList =  this.getAllEntitiesFromPosition(position);
        
        if (getFirstEntityOfType(entityList, cls) != null) {
            return true;
        }
        return false;
    }
// endregion

    /**
     * Creates an entity
     * @param entityObj     entity to be created
     */
    public void createEntity(JsonObject entityObj) {
        EntityTypes type = EntityTypes.getEntityType(entityObj.get("type").getAsString());
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        Integer layer = getAllEntitiesFromPosition(new Position(xAxis, yAxis)).size();
        createEntity(xAxis, yAxis, layer, type);
    }

    /**
     * Main switch case for the creation of all entities on the map
     * @param x         x-coordinate on the map
     * @param y         y-coordinate on the map
     * @param layer     layer on the map 
     * @param type      type of entity
     */
    public void createEntity(Integer xAxis, Integer yAxis, Integer layer, EntityTypes type) {
        switch (type) {
            case WALL:
                this.createNewEntityOnMap(new WallEntity(xAxis, yAxis, layer));
                break;
            case EXIT:
                this.createNewEntityOnMap(new ExitEntity(xAxis, yAxis, layer));
                break;
            case SWITCH:
                this.createNewEntityOnMap(new SwitchEntity(xAxis, yAxis, layer));
                break;
            case BOULDER:
                this.createNewEntityOnMap(new BoulderEntity(xAxis, yAxis, layer));
                break;
            case SPIDER:
                this.createNewEntityOnMap(new SpiderEntity(xAxis, yAxis, layer));
                break;
            case WOOD:
                this.createNewEntityOnMap(new WoodEntity(xAxis, yAxis, layer));
                break;
            case ARROW:
                this.createNewEntityOnMap(new ArrowsEntity(xAxis, yAxis, layer));
                break;
            case BOMB:
                this.createNewEntityOnMap(new BombEntity(xAxis, yAxis, layer));
                break;
            case SWORD:
                this.createNewEntityOnMap(new SwordEntity(xAxis, yAxis, layer));
                break;
            case ARMOUR:
                this.createNewEntityOnMap(new ArmourEntity(xAxis, yAxis, layer));
                break;
            case TREASURE:
                this.createNewEntityOnMap(new TreasureEntity(xAxis, yAxis, layer));
                break;
            case HEALTH_POTION:
                this.createNewEntityOnMap(new HealthPotionEntity(xAxis, yAxis, layer));
                break;
            case INVISIBILITY_POTION:
                this.createNewEntityOnMap(new InvisibilityPotionEntity(xAxis, yAxis, layer));
                break;
            case INVINCIBILITY_POTION:
                this.createNewEntityOnMap(new InvincibilityPotionEntity(xAxis, yAxis, layer));
                break;
            case MERCENARY:
                this.createNewEntityOnMap(new MercenaryEntity(xAxis, yAxis, layer));
                break;
            case ZOMBIE_TOAST:
                this.createNewEntityOnMap(new ZombieToastEntity(xAxis, yAxis, layer));
                break;
            case ZOMBIE_TOAST_SPAWNER:
                this.createNewEntityOnMap(new ZombieToastSpawnerEntity(xAxis, yAxis, layer));
                break;
            case ONE_RING:
                this.createNewEntityOnMap(new OneRingEntity(xAxis, yAxis, layer));
        }
    }

    /**
     * Creates a door and key
     * @param x         x-coordinate on the map
     * @param y         y-coordinate on the map
     * @param layer     layer on the map
     * @param keyNumber keyNumber for a corresponding pair 
     * @param type      type of entity
     */
	public void createEntity(Integer xAxis, Integer yAxis, Integer layer, Integer keyNumber, EntityTypes type) {
        switch (type) {
            case DOOR:
                this.createNewEntityOnMap(new DoorEntity(xAxis, yAxis, layer, keyNumber));
                break;
            case KEY:
                this.createNewEntityOnMap(new KeyEntity(xAxis, yAxis, layer, keyNumber));
                break;
        }
	}

    /**
     * Creates a portal with a certain colour
     * @param x         x-coordinate on the map
     * @param y         y-coordinate on the map
     * @param layer     layer on the map
     * @param colour    colour of the portal 
     * @param type      type of entity
     */
	public void createEntity(Integer xAxis, Integer yAxis, Integer layer, String colour, EntityTypes type) {
        switch (type) {
            case PORTAL:
                this.createNewEntityOnMap(new PortalEntity(xAxis, yAxis, layer, colour));
                break;
        }
	}

    /**
     * Creates an entity
     * @param x         x-coordinate on the map
     * @param y         y-coordinate on the map
     * @param type      type of entity
     */
    public void createEntity(Integer x, Integer y, EntityTypes type) {
        Integer layer = this.getAllEntitiesFromPosition(new Position(x,y)).size();
        createEntity(x, y, layer, type);
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
    public void generateEnemyEntities(String gameMode) {
        generateSpider(gameMode);
        generateZombieToast(gameMode);
        generateMercenary(gameMode);
        tickCounter++;
    }

    /**
     * Generates mercenaries based on game mode
     * @param gameMode         difficulty of the game
     */
    private void generateMercenary(String gameMode) {
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
    private void generateSpider(String gameMode) {
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
    private void generateZombieToast(String gameMode) {
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
