package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
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

    public EntitiesControl() {
        entities = new ArrayList<IEntity>();
    }

    public void addEntities(IEntity entity) {
        entities.add(entity);
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

    public List<IAutoMovingEntity> getAllAutoMovingEntities() {
        return entities.stream()
            .filter(IAutoMovingEntity.class::isInstance)
            .map(IAutoMovingEntity.class::cast)
            .collect(Collectors.toList());
    }

    public List<IEntity> getAllEntitiesFromPosition(Position position) {
        return this.entities.stream().filter(entity -> entity != null && entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    public List<IEntity> entitiesOfType(String type) {
        return this.entities.stream().filter(entity -> entity.getType().equals(type)).collect(Collectors.toList());
    }

    public List<IInteractingEntity> entitiesInteractableInRange(List<IEntity> entityList) {
        return entityList.stream().filter(entity -> entity instanceof IInteractingEntity).map(IInteractingEntity.class::cast).collect(Collectors.toList());
    }

    public static boolean containsUnpassableEntities(List<IEntity> entityList) {
        return entityList.stream().anyMatch(entity -> !entity.isPassable());
    }

    public static boolean interactingEntitiesUnpassable(List<IInteractingEntity> entityList) {
        return entityList.stream().anyMatch(entity -> !entity.isPassable());
    }

    public static IEntity entitiesContainsType(List<IEntity> entityList, Class<?> cls) {
        return entityList.stream().filter(entity -> entity.getClass().equals(cls)).findAny().orElse(null);
    }

    public boolean positionContainsEntityType(Position position, Class<?> cls) {
        List<IEntity> entityList =  this.getAllEntitiesFromPosition(position);
        
        if (entitiesContainsType(entityList, cls) != null) {
            return true;
        }
        return false;
    }

    public void createEntity(JsonObject entityObj) {
        String type = entityObj.get("type").getAsString();
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        Integer layer = getAllEntitiesFromPosition(new Position(xAxis, yAxis)).size();
        switch (type) {
            case "wall":
                this.addEntities(new WallEntity(xAxis, yAxis, layer));
                break;
            case "exit":
                this.addEntities(new ExitEntity(xAxis, yAxis, layer));
                break;
            case "door":
                this.addEntities(new DoorEntity(xAxis, yAxis, layer));
                break;
            case "portal":
                this.addEntities(new PortalEntity(xAxis, yAxis, layer, entityObj.get("colour").getAsString()));
                break;
            case "switch":
                this.addEntities(new SwitchEntity(xAxis, yAxis, layer));
                break;
            case "boulder":
                this.addEntities(new BoulderEntity(xAxis, yAxis, layer));
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
            case "key":
                this.addEntities(new KeyEntity(xAxis, yAxis, layer));
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
            case "one_ring":
                this.addEntities(new TheOneRingEntity(xAxis, yAxis, layer));
                break;
        }
    }

    public void createEntity(Integer xAxis, Integer yAxis, Integer layer, String type) {
        switch (type) {
            case "wall":
                this.addEntities(new WallEntity(xAxis, yAxis, layer));
                break;
            case "exit":
                this.addEntities(new ExitEntity(xAxis, yAxis, layer));
                break;
            case "door":
                this.addEntities(new DoorEntity(xAxis, yAxis, layer));
                break;
            case "portal":
                this.addEntities(new PortalEntity(xAxis, yAxis, layer, "BLUE"));
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
            case "key":
                this.addEntities(new KeyEntity(xAxis, yAxis, layer));
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
            case "one_ring":
                this.addEntities(new TheOneRingEntity(xAxis, yAxis, layer));
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
        return this.entitiesOfType(this.entities, type);
    }

    public List<IEntity> entitiesOfType(List<IEntity> entitiyList, String type) {
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
    }

    private void generateSpider() {
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
        try {
            return this.entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst()
                .get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
