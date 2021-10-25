package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Bomb extends Entity implements ICollectableEntity {
    public Bomb() {
        super("BombType");
        //TODO Auto-generated constructor stub
    }

    public Bomb(String id, int x, int y, String type) {
        super(id, x, y, type);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    public void explode(){
        
    }

}
