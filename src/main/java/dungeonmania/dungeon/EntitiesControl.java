package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.util.Position;
import dungeonmania.entities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.*;

public class EntitiesControl {
    private List<IEntity> entities;
    private Random rand = new Random();
    private Integer tickCounter = 0;
    private Integer entityCounter = 0;

    public EntitiesControl() {
        entities = new ArrayList<IEntity>();
    }

    public void addEntity(IEntity entity) {
        entities.add(entity);
        entityCounter++;
    }

    private void createNewEntityOnMap(IEntity entity) {
        entity.setId(Integer.toString(entityCounter));
        entities.add(entity);
        entityCounter++;
    }

    public void removeEntity(IEntity entity) {
        entities.remove(entity);
    }

    public List<IEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<IEntity> entities) {
        this.entities = entities;
    }


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

    public void moveAllMovingEntities(CharacterEntity player) {
        List<IAutoMovingEntity> movingEntities = getAllAutoMovingEntities();
        for (IAutoMovingEntity entity : movingEntities) {
            entity.move(this, player);
        }
    }

    public void runAwayAllMovingEntities(CharacterEntity player) {
        List<IAutoMovingEntity> movingEntities = getAllAutoMovingEntities();
        for (IAutoMovingEntity entity : movingEntities) {
            entity.runAway(this, player);
        }
    }

// region Filter
    public List<IAutoMovingEntity> getAllAutoMovingEntities() {
        return EntitiesControl.getEntitiesOfType(this.entities, IAutoMovingEntity.class);
    }

    public List<IContactingEntity> getInteractableEntitiesFrom(List<IEntity> entityList) {
        return EntitiesControl.getEntitiesOfType(entityList, IContactingEntity.class);
    }

    public static <T> List<T> getEntitiesOfType(List<IEntity> entityList, Class<T> cls) {
        return entityList.stream().filter(cls::isInstance).map(cls::cast).collect(Collectors.toList());
    }

    public <T extends IEntity> List<T> getAllEntitiesOfType(Class<T> cls) {
        return EntitiesControl.getEntitiesOfType(this.entities, cls);
    }

    public <T> List<T> getEntitiesOfType(Class<T> cls) {
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

    public void createEntity(JsonObject entityObj) {
        EntityTypes type = EntityTypes.getEntityType(entityObj.get("type").getAsString());
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        Integer layer = getAllEntitiesFromPosition(new Position(xAxis, yAxis)).size();
        createEntity(xAxis, yAxis, layer, type);
    }

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
        }
    }

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

	public void createEntity(Integer xAxis, Integer yAxis, Integer layer, String colour, EntityTypes type) {
        switch (type) {
            case PORTAL:
                this.createNewEntityOnMap(new PortalEntity(xAxis, yAxis, layer, colour));
                break;
        }
	}

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

    public void generateEnemyEntities() {
        generateSpider();
        generateZombieToast();
        tickCounter++;
    }

    private void generateSpider() {
        // TODO replace this with an enemy generator
        List<SpiderEntity> spiders = this.getAllEntitiesOfType(SpiderEntity.class);
        if (spiders.size() < 4) {
            Position largestCoordinate = this.getLargestCoordinate();
            int largestX = largestCoordinate.getX();
            int largestY = largestCoordinate.getY();
            int randomX = rand.nextInt(largestX);
            int randomY = rand.nextInt(largestY);
            if (getRandomBoolean((float) .05) 
                && !this.positionContainsEntityType(new Position(randomX, randomY), BoulderEntity.class)) {
                this.createEntity(randomX, randomY, EntityTypes.SPIDER);
            }
        }
    }

    private void generateZombieToast() {
        if (tickCounter % 5 == 0) {
            List<ZombieToastSpawnerEntity> spawnerEntities = getAllEntitiesOfType(ZombieToastSpawnerEntity.class);
            for (IEntity spawner : spawnerEntities) {
                this.createEntity(
                    spawner.getPosition().getX(), 
                    spawner.getPosition().getY(), 
                    EntityTypes.ZOMBIE_TOAST
                );
            }
        }
    }

    public boolean getRandomBoolean(float p){
        return rand.nextFloat() < p;
    }

    public List<IEntity> getAllAdjacentEntities(Position position) {
        List<IEntity> adjacentEntities = new ArrayList<IEntity>();
        for (IEntity ent : this.entities) {
            Position entPosition = ent.getPosition();
            if (Position.isAdjacent(position, entPosition) || Position.isAdjacent(entPosition, position)) { // why tho @braedon
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
}
