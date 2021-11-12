package dungeonmania.entities.staticEntities;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.util.DungeonEntityJsonObject;

public class PortalEntity extends Entity implements IContactingEntity {
    String colour;
    PortalEntity portalPair;
    public PortalEntity(String colour) {
        this(0, 0, colour);
    }
    
    public PortalEntity(int x, int y, String colour) {
        super(x, y, EntityTypes.PORTAL);
        this.colour = colour;
    }

    public PortalEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY(), info.getColor());
    }

    public String getColour() {
        return colour;
    }

    @Override
    public void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        PortalEntity portalPair = getPortalPair(entities);
        player.setPosition(
            portalPair.getPosition()
        );
    }

    /**
     * Uses lazy initialisation to get the portal pair from the list of all entities
     * @param entities
     * @return the corresponding portal entity
     */
    private PortalEntity getPortalPair(EntitiesControl entities) {
        if (this.portalPair == null) {
            List<PortalEntity> portalsOnMap = entities.getAllEntitiesOfType(PortalEntity.class);
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

    @Override
    public JsonObject buildJson() {
        JsonObject entityInfo = super.buildJson();
        entityInfo.addProperty("colour", this.colour);
        return entityInfo;
    }
}
