package dungeonmania.entities.staticEntities;

import dungeonmania.entities.Entity;

public class PortalEntity extends Entity {
    public PortalEntity() {
        this(0, 0, 0);
    }
    
    public PortalEntity(int x, int y, int layer) {
        super(x, y, layer, "portal");
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }
}
