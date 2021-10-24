package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.IEntity;
import dungeonmania.util.Position;

public class EntitiesControl {
    private List<IEntity> entities = new ArrayList<IEntity>();

    public EntitiesControl() {
    }

    public void addEntities(IEntity entity) {
        entities.add(entity);
    }

    public List<IEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<IEntity> entities) {
        this.entities = entities;
    }

    public List<IEntity> entitiesFromPosition(Position position) {
        // for (IEntity entity : this.entities) {
            
        // }
        return this.entities.stream().filter(entity -> entity.getPosition().equals(position)).collect(Collectors.toList());
    }

    public IEntity entitiesContainsType(String type) {
        return this.entities.stream().filter(entity -> entity.getInfo().getType().equals(type)).findAny().orElse(null);
    }

    public static IEntity entitiesContainsType(List<IEntity> entityList, String type) {
        return entityList.stream().filter(entity -> entity.getInfo().getType().equals(type)).findAny().orElse(null);
    }

    public static boolean entitiesUnpassable(List<IEntity> entityList) {
        return entityList.stream().anyMatch(entity -> !entity.passable());
    }
}
