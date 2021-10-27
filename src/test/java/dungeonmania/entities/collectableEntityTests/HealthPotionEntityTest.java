package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.HealthPotionEntity;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class HealthPotionEntityTest implements IEntityTests{
    @Override
    @Test
    public void TestEntityResponseInfo() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            health_potion,
            "health_potion-0-0-0",
            "health_potion",
            new Position(0,0),
            false
        );
    }

    @Test
    public void TestHealthPotionCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(health_potion);
        assertTrue(entities.getEntities().size() == 1);

        health_potion.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "health_potion");
        }
    }


    
}
