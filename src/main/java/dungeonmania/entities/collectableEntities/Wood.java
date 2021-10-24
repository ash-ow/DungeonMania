package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;

public class Wood extends Entity implements ICollectableEntity {
    public Wood() {
        super("WoodType");
        //TODO Auto-generated constructor stub
    }

    public Wood(String id, int x, int y, String type) {
        super(id, x, y, type);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }
}
