package dungeonmania.entities.staticEntities;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.sound.sampled.Port;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PortalEntity extends Entity implements IInteractingEntity{
    String colour;
    Boolean passable;

    public PortalEntity() {
        this(0, 0, 0, "BLUE");
        this.passable = false;
    }
    
    public PortalEntity(int x, int y, int layer, String colour) {
        super(x, y, layer, "portal");
        this.colour = colour;
        this.passable = false;
    }

    public String getColour() {
        return colour;
    }
    
    @Override
    public boolean isPassable() {
        return passable;
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
        Position target = portalPair.getPosition().translateBy(direction);
        List<IEntity> targetEntities = entities.entitiesFromPosition(target);
        if (EntitiesControl.entitiesUnpassable(targetEntities)) {
            return false;
        }
        player.setPosition(portalPair.getPosition());
        this.passable = true;
        return true;
    }
}
