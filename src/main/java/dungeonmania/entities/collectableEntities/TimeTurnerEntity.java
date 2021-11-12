package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonObject;

public class TimeTurnerEntity extends CollectableEntity{
    public TimeTurnerEntity() {
        this(0, 0);
    }

    public TimeTurnerEntity(int x, int y) {
        super(x, y, EntityTypes.TIME_TURNER);
    }

    public TimeTurnerEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }
}
