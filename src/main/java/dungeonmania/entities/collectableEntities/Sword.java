package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class Sword extends Entity implements ICollectableEntity {
    public int durability;
    
    public Sword(int durability) {
        super("SwordType");
        //TODO Auto-generated constructor stub
        this.durability = durability;
    }

    public Sword(String id, int x, int y, String type) {
        super(id, x, y, type);
        //TODO Auto-generated constructor stub
    }

    @Override
    public boolean passable() {
        // TODO Auto-generated method stub
        return false;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }

    // public void useSword(Character character) {
    //     if (durability > 0) {
    //         durability--;
    //     }
    //      if (durability == 0) {
    //          To do: Player function to remove armour     
    //     }
    // }

    public int getDurability(){
        return this.durability;
    }

    public void setDurability(int durability){
        this.durability = durability;
    }

}
