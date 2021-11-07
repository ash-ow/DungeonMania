package dungeonmania.entities.movingEntities;

import dungeonmania.util.DungeonEntityJsonObject;

public class AssassinEntity extends MercenaryEntity implements IBoss {

    public AssassinEntity(DungeonEntityJsonObject info) {
        super(info);
    }

    public AssassinEntity() {
    }

    public AssassinEntity(int x, int y) {
        super(x, y);
    }
    

}
