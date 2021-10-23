package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

public class BoulderEntity extends StaticEntity {

    public BoulderEntity(int x, int y, int layer, String type) {
        super(x, y, layer, type);
    }

    @Override
    public boolean passable() {
        return false;
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }
}
