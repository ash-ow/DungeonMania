package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.staticEntities.*;

public class EntitiesControl {
    private List<IEntity> entities;

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

    public void MovingEntities(Direction direction, CharacterEntity player) {
        List<IMovingEntity> movingEntities = entities.stream()
            .filter(entity -> entity instanceof IMovingEntity)
            .map(IMovingEntity.class::cast)
            .collect(Collectors.toList());

        for (IMovingEntity entity : movingEntities) {
            entity.move(direction, this, player);
        }

    }

    public List<IEntity> entitiesFromPosition(Position position) {
        return this.entities.stream().filter(entity -> entity != null && entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    public List<IInteractingEntity> entitiesInteractableInRange(List<IEntity> entityList) {
        return entityList.stream().filter(entity -> entity instanceof IInteractingEntity).map(IInteractingEntity.class::cast).collect(Collectors.toList());
    }

    public static boolean entitiesUnpassable(List<IEntity> entityList) {
        return entityList.stream().anyMatch(entity -> !entity.isPassable());
    }
    public static boolean interactingEntitiesUnpassable(List<IInteractingEntity> entityList) {
        return entityList.stream().anyMatch(entity -> !entity.isPassable());
    }

    public static IEntity entitiesContainsType(List<IEntity> entityList, Class<?> cls) {
        return entityList.stream().filter(entity -> entity.getClass().equals(cls)).findAny().orElse(null);
    }

    public void createEntity(Integer xAxis, Integer yAxis, Integer layer, String type) {
        switch (type) {
            case "wall":
                this.entities.add(new WallEntity(xAxis, yAxis, layer));
                break;
            case "exit":
                this.entities.add(new ExitEntity(xAxis, yAxis, layer));
                break;
            case "door":
                this.entities.add(new DoorEntity(xAxis, yAxis, layer));
                break;
            case "portal":
                this.entities.add(new PortalEntity(xAxis, yAxis, layer));
                break;
            case "switch":
                this.entities.add(new SwitchEntity(xAxis, yAxis, layer));
                break;
            case "boulder":
                this.entities.add(new BoulderEntity(xAxis, yAxis, layer));
                break;
            case "spider":
                this.entities.add(new SpiderEntity(xAxis, yAxis, layer));
                break;
        }
    }

    public List<IEntity> entitiesOfSameType(String type) {
        List<IEntity> sameType = new ArrayList<>();
        for (IEntity entity : entities) {
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
}
