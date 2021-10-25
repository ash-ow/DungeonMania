package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;

public class Sword extends Entity implements ICollectableEntity {
    public Sword() {
        super("SwordType");
        //TODO Auto-generated constructor stub
    }

    public Sword(String id, int x, int y, String type) {
        super(id, x, y, type);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }
}
