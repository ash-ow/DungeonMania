package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;

public class InventoryTests{
    @Test
    public void TestMultipleItemsCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArmourEntity armour = new ArmourEntity(0,0,0);
        SwordEntity sword = new SwordEntity(0,1,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(armour);
        entities.addEntities(sword);
        assertEquals(2, entities.getEntities().size());

        // Pick up first item
        armour.interactWithPlayer(entities, Direction.RIGHT, player);
        assertEquals(1, player.getInventoryInfo().size());
        assertEquals(1, entities.getEntities().size());

        // Pick up second item
        sword.interactWithPlayer(entities, Direction.RIGHT, player);
        assertEquals(2, player.getInventoryInfo().size());

        assertEquals(0, entities.getEntities().size());

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
