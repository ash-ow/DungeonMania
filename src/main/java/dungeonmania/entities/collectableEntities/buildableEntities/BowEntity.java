package dungeonmania.entities.collectableEntities.buildableEntities;

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
        this.requiredComponents.put(new Wood(), 2);
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }
}
