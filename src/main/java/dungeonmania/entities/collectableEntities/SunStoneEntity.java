package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonParser;

public class SunStoneEntity extends CollectableEntity {
    public SunStoneEntity() {
        this(0,0);
    }

    public SunStoneEntity(int x, int y) {
        super(x, y, EntityTypes.SUN_STONE);
    }
    
    public SunStoneEntity(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }
}
