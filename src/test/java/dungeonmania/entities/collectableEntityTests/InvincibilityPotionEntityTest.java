package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvincibilityPotionEntity;

import dungeonmania.entities.IEntityTests;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class InvincibilityPotionEntityTest implements ICollectableEntityTest {
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
    @Override
    public void TestCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(invincibility_potion);
        assertEquals(1, entities.getEntities().size());

        invincibility_potion.interactWithPlayer(entities, Direction.RIGHT, player);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "invincibility_potion");
        }
    
    }
    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
    }    
}