package dungeonmania.entities.movingEntities;

import com.google.gson.JsonObject;

import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.collectableEntities.SunStoneEntity;
import dungeonmania.exceptions.InvalidActionException;


public class AssassinEntity extends MercenaryEntity implements IBoss {

    public AssassinEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    public AssassinEntity() {
        this(0, 0);
    }

    
    public AssassinEntity(int x, int y) {
        super(x, y, EntityTypes.ASSASSIN);
        this.damage = 5;
        this.health = 80;
    }
    
    /**
     * Determines the interactions of the assassin with the player based on range and whether they have treasure/sunstone AND one ring
     * @param player the player with which the assassin will interact with 
     */
    //TO DO: implement player.useItem(); reduce hardcoding of sun_stone with treasure
    @Override
    public void interactWith(CharacterEntity player) throws InvalidActionException {
        TreasureEntity treasureFound = (TreasureEntity) player.getInventory().getFirstItemOfType(TreasureEntity.class);
        IEntity oneRingFound = player.getInventory().getFirstItemOfType(OneRingEntity.class);
        if (oneRingFound == null) {
            throw new InvalidActionException("Player has no one ring");
        }
        if (treasureFound == null) {
           throw new InvalidActionException("Player has no treasure");
        }
        if (!isInRange(player)) {
            throw new InvalidActionException("Player is too far away");
        }
        treasureFound.used(player);
        player.getInventoryItems().remove(oneRingFound);
        player.addTeammates(this);
        this.isBribed = true;   
    }

    @Override
    public boolean removeAfterInteraction() {
        return false;
    }

    @Override
    public float getDamage() {
        return 5;
    }

    @Override
    public void deactivteSpecialAbility() {
        // This boss does not have special ability
    }

}
