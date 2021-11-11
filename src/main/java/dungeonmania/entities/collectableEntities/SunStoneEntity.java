package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.EntityTypes;

import dungeonmania.util.DungeonEntityJsonObject;
public class SunStoneEntity extends TreasureEntity implements ITreasure {

    public SunStoneEntity() {
        this(0,0);
    }

     /**
     * Sun Stone constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public SunStoneEntity(int x, int y) {
        super(x, y, EntityTypes.SUN_STONE);
    }

    public SunStoneEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }
    
    

    

}
