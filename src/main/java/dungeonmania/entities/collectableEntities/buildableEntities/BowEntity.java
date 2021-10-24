package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.collectableEntities.Arrows;
import dungeonmania.entities.collectableEntities.Wood;

public class BowEntity extends BuildableEntity {
    public BowEntity() {
        super("BowType");
    }

    protected BowEntity(String id, int x, int y, String type) {
        super(id, x, y, type);
    }
    
    @Override
    public void initialiseRequiredComponents() {
        this.requiredComponents.put(new Wood(), 1);
        this.requiredComponents.put(new Arrows(), 3);
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }
}
