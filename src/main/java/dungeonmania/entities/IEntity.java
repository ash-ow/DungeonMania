package dungeonmania.entities;

import javax.swing.text.html.parser.Entity;

import dungeonmania.response.models.*;
import dungeonmania.util.Position;

public interface IEntity {
    public EntityResponse getInfo();
    public Position getPosition();
    public boolean passable();
}
