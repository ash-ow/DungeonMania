package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.dungeon.Instruction;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.DungeonEntityJsonParser;

public class OlderCharacter extends CharacterEntity implements IAutoMovingEntity{
    private List<Instruction> ticks;
    private int currentInstruction = 0;
    
    /**
     * Character constructor
     * @param x          x-coordinate on the map
     * @param y          y-coordinate on the map
     * @param layer      layer on the map
     * @param gameMode   denotes the difficulty settings of the game 
     */
    public OlderCharacter(int x, int y, GameModeType gameMode, List<Instruction> ticks) {
        super(x, y, EntityTypes.OLDER_PLAYER, gameMode);
        this.ticks = new ArrayList<>(ticks);
    }

    public OlderCharacter(DungeonEntityJsonParser info, GameModeType gameMode, List<Instruction> ticks) {
        this(info.getX(), info.getY(), gameMode, ticks);
    }

    @Override
    public void move(EntitiesControl entitiesControl, CharacterEntity player) {
        if (currentInstruction >= ticks.size()) {
            entitiesControl.removeEntity(this);
        } else {
            String itemID = this.ticks.get(currentInstruction).getItemID();
            Direction dir = this.ticks.get(currentInstruction).getDirection();
            if (itemID != null) {
                try {
                    super.useItem(itemID, entitiesControl);
                } catch (InvalidActionException e) {
                    entitiesControl.removeEntity(this);
                }
            } else {
                super.move(dir, entitiesControl);
                if (this.isInSamePositionAs(player)) {
                    contactWithPlayer(entitiesControl, player);
                }
            }
            currentInstruction++;
        }
        
    }

    @Override
    public void setMoveBehvaiour(IMovingBehaviour newBehaviour) {}
}
