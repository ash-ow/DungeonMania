package dungeonmania.entities.staticEntities;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;

public class PortalEntity extends Entity implements IInteractingEntity{
    String colour;

    public PortalEntity() {
        this(0, 0, 0, "BLUE");
    }
    
    public PortalEntity(int x, int y, int layer, String colour) {
        super(x, y, layer, "portal");
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }
    
    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public boolean interactWithPlayer(EntitiesControl entities, Direction direction, CharacterEntity player) {
        List<PortalEntity> portalsOnMap = entities.entitiesOfType("portal").stream().map(PortalEntity.class::cast).collect(Collectors.toList());
        PortalEntity portalPair = null;
        for (PortalEntity portal : portalsOnMap) {
            if (!portal.equals(this) && portal.getColour().equals(this.colour)) {
                portalPair = portal;
            }
        }
        if (portalPair == null) {
            throw new IllegalArgumentException("Portal does not have pair");
        }
        player.setPosition(portalPair.getPosition());
        return true;
    }
}
