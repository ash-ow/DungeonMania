package dungeonmania.entities.staticEntities;

public class WallEntity extends StaticEntity{

    public WallEntity(int x, int y, int layer, String type) {
        super(x, y, layer, type);
    }
    
    @Override
    public boolean passable() {
        return false;
    }
}
