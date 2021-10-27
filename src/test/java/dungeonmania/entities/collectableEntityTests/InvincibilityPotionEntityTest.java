package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvincibilityPotionEntity;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class InvincibilityPotionEntityTest implements IEntityTests{
    @Override
    @Test
    public void TestEntityResponseInfo() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            invincibility_potion,
            "invincibility_potion-0-0-0",
            "invincibility_potion",
            new Position(0,0),
            false
        );
    }
    @Test
    public void TestInvisibilityPotionCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(invincibility_potion);
        assertTrue(entities.getEntities().size() == 1);

        invincibility_potion.interactWithPlayer(entities, Direction.RIGHT, player);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "invincibility_potion");
        }
    
    }



    
}