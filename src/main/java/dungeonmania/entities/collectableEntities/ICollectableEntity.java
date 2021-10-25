package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.IEntity;
import dungeonmania.util.Position;

public interface ICollectableEntity extends IEntity {
    void setPosition(Position position);
}
