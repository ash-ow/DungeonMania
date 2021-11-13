package dungeonmania.dungeon;

import java.io.ObjectInputStream.GetField;

import dungeonmania.util.Direction;

public class Instruction {
    private String itemID;
    private Direction direction;
    public Instruction(String itemID, Direction direction) {
        this.itemID = itemID;
        this.direction = direction;
    }

    public Instruction(Direction direction) {
        this.itemID = null;
        this.direction = direction;
    }

    public Instruction(String itemID) {
        this.itemID = itemID;
        this.direction = Direction.NONE;
    }

    public String getItemID() {
        return itemID;
    }

    public Direction getDirection() {
        return direction;
    }
}
