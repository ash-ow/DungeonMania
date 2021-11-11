package dungeonmania.entities.staticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonObject;
import dungeonmania.util.Position;

public class WireEntity extends Entity {

    public WireEntity(int x, int y, EntityTypes type) {
        super(x, y, type);
    }

	public WireEntity(DungeonEntityJsonObject jsonInfo) {
        this(jsonInfo.getX(), jsonInfo.getY(), EntityTypes.WIRE);
	}

    /**
     * Recursively follows a path of wires until a switch is reached
     * @param position from which the wire is being followed
     * @return a list of all the switches in contact with the wire
     */
    public List<SwitchEntity> followWire(Position position) {
        // TODO
        // pass the position of the entity that is following the wire
        // get a list of all cardinally adjacent entities
        // filter them by instance of Wire or Switch entity
        // loop through that list 
        // if wire, the run followWire recursively
        // if Switch (which must happen eventually) then return the switch
        return new ArrayList<SwitchEntity>();
    }

}
