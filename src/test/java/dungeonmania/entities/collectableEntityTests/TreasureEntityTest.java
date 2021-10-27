package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.TreasureEntity;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class TreasureEntityTest implements IEntityTests{
    @Override
    @Test
    public void TestEntityResponseInfo() {
        TreasureEntity treasure = new TreasureEntity(0,0,0);
        assertEntityResponseInfoEquals(
            treasure,
            "treasure-0-0-0",
            "treasure",
            new Position(0,0),
            false
        );
    }

    @Test
    public void TestTreasureCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        TreasureEntity treasure = new TreasureEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(treasure);
        assertTrue(entities.getEntities().size() == 1);

        treasure.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "treasure");
        }
    }

    
}