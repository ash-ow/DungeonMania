package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class Armour extends Entity implements ICollectableEntity {
    public int durability;    

    public Armour(int durability) {
        super("ArmourType");
        //TODO Auto-generated constructor stub
        this.durability = durability;
    }

    public Armour(String id, int x, int y, String type) {
        super(id, x, y, type);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }

    public void useArmour(Character character) {
        // INCOMPLETE
        if (durability > 0) {
            durability--;
        }
        if (durability == 0) {     
        }
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDurability(){
        return this.durability;
    }

    public void setDurability(int durability){
        this.durability = durability;
    }

    @Override
    public void collected(){

    }

    @Override
    public void used(){

    }

}
