package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;

public class Arrows extends Entity implements ICollectableEntity {
    public Arrows() {
        super("ArrowsType");
        //TODO Auto-generated constructor stub
    }

    public Arrows(String id, int x, int y, String type) {
        super(id, x, y, type);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }
}