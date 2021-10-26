package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.util.Position;
import dungeonmania.entities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.staticEntities.*;

public class EntitiesControl {
    private List<IEntity> entities = new ArrayList<IEntity>();

    public EntitiesControl() {
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

    public List<IEntity> entitiesFromPosition(Position position) {
        return this.entities.stream().filter(entity -> entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    public List<IInteractingEntity> entitiesInteractableInRange(List<IEntity> entityList) {
        return entityList.stream().filter(entity -> entity instanceof IInteractingEntity).map(IInteractingEntity.class::cast).collect(Collectors.toList());
    }

    public static boolean entitiesUnpassable(List<IEntity> entityList) {
        return entityList.stream().anyMatch(entity -> !entity.isPassable());
    }

    public void createEntity(Integer xAxis, Integer yAxis, Integer layer, String type) {
        IEntity ent;
        switch (type) {
            case "wall":
                ent = new WallEntity(xAxis, yAxis, layer);
                break;
            case "exit":
                ent = new ExitEntity(xAxis, yAxis, layer);
                break;
            case "door":
                ent = new DoorEntity(xAxis, yAxis, layer);
                break;
            case "portal":
                ent = new PortalEntity(xAxis, yAxis, layer);
                break;
            case "switch":
                ent = new SwitchEntity(xAxis, yAxis, layer);
                break;
            case "boulder":
                ent = new BoulderEntity(xAxis, yAxis, layer);
                break;
            default:
                ent = null;
                break;
        }
        this.entities.add(ent);
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
}
