package dungeonmania.entities.staticEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;

public class PortalEntity extends Entity implements IInteractingEntity{
    String colour;

    public PortalEntity() {
        this(0, 0, 0, "blue");
    }
    
    public PortalEntity(int x, int y, int layer, String colour) {
        super(x, y, layer, "portal");
        this.colour = colour;
    }
    
    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        // TODO Auto-generated method stub
        return true;
    }
}
