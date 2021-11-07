package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;

public class SunStoneEntity extends CollectableEntity {
    public SunStoneEntity() {
        this(0,0,0);
    }

    public SunStoneEntity(int x, int y, int layer) {
        super(x, y, layer, EntityTypes.SUN_STONE);
    }
}
