package dungeonmania.entities.collectableEntities;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.moveBehaviour.Freeze;


public class InvisibilityPotionEntity extends CollectableEntity implements ITicker {
    CharacterEntity player;

    /**
     * Invisibility potion constructor
     */
    public InvisibilityPotionEntity() {
        this(0, 0);
    }
    
    /**
     * Invisibility Potion constructor
     * @param x x-coordinate on the map
     * @param y y-coordinate on the map
     */
    public InvisibilityPotionEntity(int x, int y) {
        super(x, y, EntityTypes.INVISIBILITY_POTION);
    }

    public InvisibilityPotionEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    /**
     * Gives the player invisibility and removes the potion from the players inventory
     * @param player CharacterEntity using the potion
     */
    @Override
    public void used(CharacterEntity player) {

        player.getInventory().getItems().remove(this);
        player.addActiveItem(this);
        this.durability = 10;
    }

    @Override
    public void tick(EntitiesControl entitiesControl) {
        if (this.durability == 0) {
            player.removeActiveItem(this);
            entitiesControl.setMovingEntitiesBehaviour(null);
        }
        List<MercenaryEntity> mercenaries = entitiesControl.getAllEntitiesOfType(MercenaryEntity.class);
        for (MercenaryEntity mercenary : mercenaries) {
            mercenary.setMoveBehaviour(new Freeze());
        }
        this.decrementDurability();
    }
}
