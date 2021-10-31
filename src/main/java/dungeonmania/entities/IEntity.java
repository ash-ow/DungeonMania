package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public interface IEntity {
    public String getId();
    public EntityTypes getType();
    public EntityResponse getInfo();
    public Position getPosition();
    public void setPosition(Position position);
	public boolean isInSamePositionAs(IEntity ent);
    public void setId(String string);
}
