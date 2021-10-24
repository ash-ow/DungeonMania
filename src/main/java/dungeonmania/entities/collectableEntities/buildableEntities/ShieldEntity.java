package dungeonmania.entities.collectableEntities.buildableEntities;

import dungeonmania.entities.collectableEntities.Wood;

public class ShieldEntity extends BuildableEntity {
    public ShieldEntity() {
        super("ShieldType");
    }

    protected ShieldEntity(String id, int x, int y, String type) {
        super(id, x, y, type);
    }
    
    @Override
    public void initialiseRequiredComponents() {
        // TODO Get real required components
        this.requiredComponents.put(new Wood(), 2);
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }

}
