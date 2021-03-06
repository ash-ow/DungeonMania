package dungeonmania.entities.collectableEntities;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.moveBehaviour.RunAway;


public class InvincibilityPotionEntity extends CollectableEntity implements IAffectingEntity {
    CharacterEntity player;
    
    /**
     * Invincibility potion constructor
     */
    public InvincibilityPotionEntity() {
        this(0, 0);
    }
    
    /**
     * Invincibility Potion constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public InvincibilityPotionEntity(int x, int y) {
        super(x, y, EntityTypes.INVINCIBILITY_POTION);
        this.durability = 0;
    }

    public InvincibilityPotionEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    /**
     * Gives the player invincibiility and removes the potion from the players inventory
     * @param player CharacterEntity using the potion
     */
    @Override
    public void used(CharacterEntity player) {
        if (!player.getGameMode().equals(GameModeType.HARD)){
            player.addActiveItem(this);
            this.durability = 10;
        }
        player.getInventory().getItems().remove(this);
    }

    @Override
    public boolean effect(EntitiesControl entitiesControl) {
        this.decrementDurability();
        if (this.durability == 0) {
            entitiesControl.setMovingEntitiesBehaviour(null);
            return true;
        }
        entitiesControl.setMovingEntitiesBehaviour(new RunAway());
        return false;
    }
}
