package dungeonmania.entities;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public interface IEntity {
    public EntityResponse getInfo();
    public Position getPosition();
    public boolean passable();
}
