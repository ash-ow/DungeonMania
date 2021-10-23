package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public interface IEntity {
    public EntityResponse getInfo();
    public Position getPosition();
    public boolean passable();
    
	public default boolean isInSamePositionAs(IEntity ent) {
        return this.getPosition().equals(ent.getPosition());
    }
}
