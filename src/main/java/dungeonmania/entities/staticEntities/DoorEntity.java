package dungeonmania.entities.staticEntities;

public class DoorEntity extends StaticEntity {

    public DoorEntity(int x, int y, int layer, String type) {
        super(x, y, layer, type);
    }
    @Override
    public boolean passable() {
        return false;
    }
}
