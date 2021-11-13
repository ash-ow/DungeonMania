package dungeonmania.entities.staticEntities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;

import dungeonmania.util.Position;

public class WireEntity extends Entity {

    public WireEntity() {
        this(0, 0);
    }

    public WireEntity(int x, int y) {
        super(x, y, EntityTypes.WIRE);
    }

	public WireEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
	}

    /**
     * Recursively follows a path of wires until a switch is reached
     * @param position from which the wire is being followed
     * @return a list of all the switches in contact with the wire
     */
    public List<SwitchEntity> followWire(Position position, EntitiesControl entitiesControl) {
        List<WireEntity> checkedWires = new ArrayList<WireEntity>();
        return this.followWireInternal(position, entitiesControl, checkedWires);
    }

    List<SwitchEntity> followWireInternal(Position position, EntitiesControl entitiesControl, List<WireEntity> checkedWires) {
        List<IEntity> adjacentEntities = getEntitiesThatHaveNotAlreadyBeenFollowed(entitiesControl, checkedWires);
        List<WireEntity> adjacentWires = EntitiesControl.getEntitiesOfType(adjacentEntities, WireEntity.class);
        List<SwitchEntity> adjacentSwitches = EntitiesControl.getEntitiesOfType(adjacentEntities, SwitchEntity.class);
        checkedWires.add(this);
        
        for (WireEntity wire : adjacentWires) {
            adjacentSwitches.addAll(wire.followWireInternal(this.getPosition(), entitiesControl, checkedWires));
        }
        
        return new ArrayList<SwitchEntity>(new HashSet<SwitchEntity>(adjacentSwitches));
    }

    private List<IEntity> getEntitiesThatHaveNotAlreadyBeenFollowed(EntitiesControl entitiesControl, List<WireEntity> checkedWires) {
        return entitiesControl
            .getAllAdjacentEntities(this.getPosition())
            .stream()
            .filter(e -> !e.getPosition().equals(position))
            .filter(e -> !checkedWires.contains(e))
            .collect(Collectors.toList());
    }
}
