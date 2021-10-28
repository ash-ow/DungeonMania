package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;

public class InventoryTests{
    @Test
    public void TestMultipleItemsCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArmourEntity armour = new ArmourEntity(0,0,0);
        SwordEntity sword = new SwordEntity(0,1,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(armour);
        entities.addEntities(sword);
        assertTrue(entities.getEntities().size() == 2);

        // Pick up first item
        armour.interactWithPlayer(entities, Direction.RIGHT, player);
        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 1);

        // Pick up second item
        sword.interactWithPlayer(entities, Direction.RIGHT, player);
        assertTrue(player.getInventoryInfo().size() == 2);

        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        
        List<IEntity> expected = new ArrayList<>();
        expected.add(armour);
        expected.add(sword);

        int i = 0;
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), expected.get(i).getType());
            i++;
        }

    }

}
