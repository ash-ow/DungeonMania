package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public interface IEntity {
    public String getId();
    public String getType();
    public EntityResponse getInfo();
    public Position getPosition();
    public boolean isPassable();
	public boolean isInSamePositionAs(IEntity ent);
}
