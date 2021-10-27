package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;

public class InventoryTests{
    @Test
    public void TestWoodCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        WoodEntity wood = new WoodEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(wood);
        assertTrue(entities.getEntities().size() == 1);

        wood.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "wood");
        }
    }

    @Test
    public void TestArrowsCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArrowsEntity arrows = new ArrowsEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(arrows);
        assertTrue(entities.getEntities().size() == 1);

        arrows.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "arrow");
        }
    }

    @Test
    public void TestBombCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        BombEntity bomb = new BombEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(bomb);
        assertTrue(entities.getEntities().size() == 1);

        bomb.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "bomb");
        }
    }

    @Test
    public void TestSwordCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SwordEntity sword = new SwordEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(sword);
        assertTrue(entities.getEntities().size() == 1);

        sword.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() == 1);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "sword");
        }
    }

    @Test
    public void TestArmourCollect() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        ArmourEntity armour = new ArmourEntity(0,0,0);
        EntitiesControl entities = new EntitiesControl();
        
        entities.addEntities(armour);
        assertTrue(entities.getEntities().size() == 1);

        armour.interactWithPlayer(entities, Direction.RIGHT, player);

        assertTrue(player.getInventoryInfo().size() > 0);
        assertTrue(entities.getEntities().size() == 0);

        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "armour");
        }
    }

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
