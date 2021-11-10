package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonObject;
public class SunStoneEntity extends CollectableEntity {
    public SunStoneEntity() {
        this(0,0);
    }

    public SunStoneEntity(int x, int y) {
        super(x, y, EntityTypes.SUN_STONE);
    }

    public SunStoneEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }
}
