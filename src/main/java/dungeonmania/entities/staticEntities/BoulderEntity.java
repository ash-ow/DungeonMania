package dungeonmania.entities.staticEntities;

public class BoulderEntity extends StaticEntity {

    public BoulderEntity(int x, int y, int layer, String type) {
        super(x, y, layer, type);
    }

    @Override
    public boolean passable() {
        return false;
    }
}
