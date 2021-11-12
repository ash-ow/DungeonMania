package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.dungeon.Instruction;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.movingEntities.moveBehaviour.IMovingBehaviour;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.DungeonEntityJsonObject;

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

    public OlderCharacter(DungeonEntityJsonObject info, GameModeType gameMode, List<Instruction> ticks) {
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
                    throw new InvalidActionException("You broke the space time continuum");
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

    /**
     * This class is auto moving but never changes its move behaviour
     */
    @Override
    public void setMoveBehaviour(IMovingBehaviour newBehaviour) {}

    /**
     * Picks up all the collectable under entity feet when it is spawned in
     * @param entitiesControl
     */
    public void pickupAllCollectables(EntitiesControl entitiesControl) {
        List<IEntity> targetEntities = entitiesControl.getAllEntitiesFromPosition(this.position);
        List<CollectableEntity> collectables = EntitiesControl.getEntitiesOfType(targetEntities, CollectableEntity.class);
        for (CollectableEntity c : collectables) {
            c.contactWithPlayer(entitiesControl, this);
        }
    }
}
