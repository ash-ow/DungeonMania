package dungeonmania.entities.staticEntities;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Position;

public class PortalEntity extends Entity implements IInteractingEntity{
    String colour;
    PortalEntity portalPair;
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
    public void interactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        PortalEntity portalPair = getPortalPair(entities);
        Position positionShift = Position.calculatePositionBetween(this.getPosition(), portalPair.getPosition());
        player.setPosition(
            player.getPosition().translateBy(positionShift)  
        );
    }

    /**
     * Uses lazy initialisation to get the portal pair from the list of all entities
     * @param entities
     * @return the corresponding portal entity
     */
    private PortalEntity getPortalPair(EntitiesControl entities) {
        if (this.portalPair == null) {
            List<PortalEntity> portalsOnMap = entities.getAllEntitiesOfType("portal").stream().map(PortalEntity.class::cast).collect(Collectors.toList());
            for (PortalEntity portal : portalsOnMap) {
                if (!portal.equals(this) && portal.getColour().equals(this.colour)) {
                    this.portalPair = portal;
                }
            }
            if (this.portalPair == null) {
                throw new IllegalArgumentException("Portal does not have pair");
            }
        }
        return this.portalPair;
    }
}
