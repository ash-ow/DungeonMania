package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.DungeonEntityJsonObject;

public class AssassinEntity extends MercenaryEntity implements IBoss {

    public AssassinEntity(DungeonEntityJsonObject info) {
        this(info.getX(), info.getY());
    }

    public AssassinEntity() {
        this(0, 0);
    }

    public AssassinEntity(int x, int y) {
        super(x, y, EntityTypes.ASSASSIN);
        this.damage = 5;
        this.health = 80;
    }
    
    @Override
    public void interactWith(CharacterEntity player) throws InvalidActionException {
        IEntity treasureFound = EntitiesControl.getFirstEntityOfType(player.getInventory(), TreasureEntity.class);
        IEntity oneRingFound = EntitiesControl.getFirstEntityOfType(player.getInventory(), OneRingEntity.class);
        if (oneRingFound == null) {
            throw new InvalidActionException("Player has no one ring");
        }
        if (treasureFound == null) {
            throw new InvalidActionException("Player has no treasure");
        }
        if (!isInRange(player)) {
            throw new InvalidActionException("Player is too far away");
        }
        player.removeEntityFromInventory(treasureFound);
        player.addTeammates(this);
        this.isBribed = true;       
    }

}
