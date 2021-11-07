package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.EntityTypes;
import dungeonmania.util.DungeonEntityJsonParser;

public class OlderCharacter extends CharacterEntity{
    public OlderCharacter() {
        this(0, 0);
    }
    
    /**
     * Character constructor
     * @param x     x-coordinate on the map
     * @param y     y-coordinate on the map
     * @param layer layer on the map 
     */
    public OlderCharacter(int x, int y) {
        this(x, y, GameModeType.STANDARD);
    }
    
    /**
     * Character constructor
     * @param x          x-coordinate on the map
     * @param y          y-coordinate on the map
     * @param layer      layer on the map
     * @param gameMode   denotes the difficulty settings of the game 
     */
    public OlderCharacter(int x, int y, GameModeType gameMode) {
        super(x, y, EntityTypes.PLAYER);
    }

    public OlderCharacter(DungeonEntityJsonParser info) {
        this(info.getX(), info.getY());
    }
}
