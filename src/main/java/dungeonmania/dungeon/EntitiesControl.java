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

    public void addEntities(IEntity entity) {
        entities.add(entity);
        entity.setId(Integer.toString(entityCounter));
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

    public void moveAllMovingEntities(Direction direction, CharacterEntity player) {
        List<IAutoMovingEntity> movingEntities = getAllAutoMovingEntities();
        for (IAutoMovingEntity entity : movingEntities) {
            entity.move(this, player);
        }
    }

// region Filter
    public List<IAutoMovingEntity> getAllAutoMovingEntities() {
        return EntitiesControl.getEntitiesOfType(this.entities, IAutoMovingEntity.class);
    }

    public List<IInteractingEntity> getInteractableEntitiesFrom(List<IEntity> entityList) {
        return EntitiesControl.getEntitiesOfType(entityList, IInteractingEntity.class);
    }

    public static <T> List<T> getEntitiesOfType(List<IEntity> entityList, Class<T> cls) {
        return entityList.stream().filter(cls::isInstance).map(cls::cast).collect(Collectors.toList());
    }

    public List<IEntity> getAllEntitiesFromPosition(Position position) {
        return this.entities.stream().filter(entity -> entity != null && entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    public static boolean containsBlockingEntities(List<IEntity> entityList) {
        return entityList.stream().filter(IBlocker.class::isInstance).map(IBlocker.class::cast).anyMatch(IBlocker::isBlocking);
    }

    public static IEntity getFirstEntityOfType(List<IEntity> entityList, Class<?> cls) {
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
        String type = entityObj.get("type").getAsString();
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        Integer layer = getAllEntitiesFromPosition(new Position(xAxis, yAxis)).size();
        createEntity(xAxis, yAxis, layer, type);
    }

    public void createEntity(Integer xAxis, Integer yAxis, Integer layer, String type) {
        switch (type) {
            case "wall":
                this.addEntities(new WallEntity(xAxis, yAxis, layer));
                break;
            case "exit":
                this.addEntities(new ExitEntity(xAxis, yAxis, layer));
                break;
            case "switch":
                this.addEntities(new SwitchEntity(xAxis, yAxis, layer));
                break;
            case "boulder":
                this.addEntities(new BoulderEntity(xAxis, yAxis, layer));
                break;
            case "spider":
                this.addEntities(new SpiderEntity(xAxis, yAxis, layer));
                break;
            case "wood":
                this.addEntities(new WoodEntity(xAxis, yAxis, layer));
                break;
            case "arrow":
                this.addEntities(new ArrowsEntity(xAxis, yAxis, layer));
                break;
            case "bomb":
                this.addEntities(new BombEntity(xAxis, yAxis, layer));
                break;
            case "sword":
                this.addEntities(new SwordEntity(xAxis, yAxis, layer));
                break;
            case "armour":
                this.addEntities(new ArmourEntity(xAxis, yAxis, layer));
                break;
            case "treasure":
                this.addEntities(new TreasureEntity(xAxis, yAxis, layer));
                break;
            case "health_potion":
                this.addEntities(new HealthPotionEntity(xAxis, yAxis, layer));
                break;
            case "invisibility_potion":
                this.addEntities(new InvisibilityPotionEntity(xAxis, yAxis, layer));
                break;
            case "invincibility_potion":
                this.addEntities(new InvincibilityPotionEntity(xAxis, yAxis, layer));
                break;
            case "mercenary":
                this.addEntities(new MercenaryEntity(xAxis, yAxis, layer));
                break;
            case "zombie_toast":
                this.addEntities(new ZombieToastEntity(xAxis, yAxis, layer));
                break;
            case "zombie_toast_spawner":
                this.addEntities(new ZombieToastSpawnerEntity(xAxis, yAxis, layer));
                break;
        }
    }

	public void createEntity(Integer xAxis, Integer yAxis, Integer layer, Integer keyNumber, String type) {
        switch (type) {
            case "door":
                this.addEntities(new DoorEntity(xAxis, yAxis, layer, keyNumber));
                break;
            case "key":
                this.addEntities(new KeyEntity(xAxis, yAxis, layer, keyNumber));
                break;
        }
	}

	public void createEntity(Integer xAxis, Integer yAxis, Integer layer, String colour, String type) {
        switch (type) {
            case "portal":
                this.addEntities(new PortalEntity(xAxis, yAxis, layer, colour));
                break;
        }
	}

    public void createEntity(Integer x, Integer y, String type) {
        Integer layer = getNumberOfEntitiesInPosition(new Position(x, y));
        createEntity(x, y, layer, type);
    }

    private Integer getNumberOfEntitiesInPosition(Position position) {
        return this.getAllEntitiesFromPosition(this.getLargestCoordinate()).size();
    }

    public List<IEntity> getAllEntitiesOfType(String type) {
        return EntitiesControl.getEntitiesOfType(this.entities, type);
    }

    public static List<IEntity> getEntitiesOfType(List<IEntity> entitiyList, String type) {
        // TODO refactor to accept Class<?> instead of string type
        List<IEntity> sameType = new ArrayList<>();
        for (IEntity entity : entitiyList) {
            if (entity.getInfo().getType().equals(type)) {
                sameType.add(entity);
            }
        }
        return sameType;
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
        List<IEntity> spiders = this.getAllEntitiesOfType("spider");
        if (spiders.size() < 4) {
            Position largestCoordinate = this.getLargestCoordinate();
            int largestX = largestCoordinate.getX();
            int largestY = largestCoordinate.getY();
            int randomX = rand.nextInt(largestX);
            int randomY = rand.nextInt(largestY);
            if (getRandomBoolean((float) .05) 
                && !this.positionContainsEntityType(new Position(randomX, randomY), BoulderEntity.class)) {
                this.createEntity(randomX, randomY, "spider");
            }
        }
    }

    private void generateZombieToast() {
        if (tickCounter % 5 == 0) {
            List<IEntity> spawnerEntities = getAllEntitiesOfType("zombie_toast_spawner");
            for (IEntity spawner : spawnerEntities) {
                this.createEntity(
                    spawner.getPosition().getX(), 
                    spawner.getPosition().getY(), 
                    "zombie_toast"
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
            if (Position.isAdjacent(position, entPosition)) {
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
