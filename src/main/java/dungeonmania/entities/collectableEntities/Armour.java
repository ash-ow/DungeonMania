package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;

public class Armour extends Entity implements ICollectableEntity {
    public Armour() {
        super("ArmourType");
        //TODO Auto-generated constructor stub
    }

    public Armour(String id, int x, int y, String type) {
        super(id, x, y, type);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }
}
