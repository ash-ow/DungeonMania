package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.buildableEntities.ShieldEntity;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CharacterEntityTest implements IMovingEntityTest, IEntityTests, IBattlingEntityTest {
    @Test
    public void TestMove() {
        CharacterEntity character = new CharacterEntity();
        assertPositionEquals(character.getPosition(), 0, 0);
        
        character.move(Direction.DOWN);
        assertPositionEquals(character.getPosition(), 0, 1);
        
        character.move(Direction.UP);
        assertPositionEquals(character.getPosition(), 0, 0);
        
        character.move(Direction.LEFT);
        assertPositionEquals(character.getPosition(), -1, 0);
        
        character.move(Direction.RIGHT);
        assertPositionEquals(character.getPosition(), 0, 0);
    }

    @Test
    public void testSimpleRestriction() {
        CharacterEntity player = new CharacterEntity(0, 4, 0);
        WallEntity wall = new WallEntity(0, 3, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(wall);
        String jsonGoals = "{ \"goal\": \"exit\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        assertEquals(player.getPosition(), new Position(0, 4));
        dungeon.tick(Direction.UP);
        assertEquals(player.getPosition(), new Position(0, 4));
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        CharacterEntity player = new CharacterEntity(0, 4, 0);
        assertEntityResponseInfoEquals(
            player,
            "player-0-4-0",
            "player",
            new Position(0,4),
            false
        );
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(character);
        entitiesControl.addEntity(zombie);
        character.setHealth(2);
        zombie.battle(entitiesControl, character);
        assertFalse(character.isAlive());
    }

    @Test
    public void TestInventory() {
        CharacterEntity character = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        character.addEntityToInventory(wood);
        assertEquals(1, character.getInventory().size());
        character.removeEntityFromInventory(wood);
        assertEquals(0, character.getInventory().size());
    }

    @Test
    public void TestBuildBow() {
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        ArrowsEntity arrow1 = new ArrowsEntity();
        ArrowsEntity arrow2 = new ArrowsEntity();
        ArrowsEntity arrow3 = new ArrowsEntity();
        player.addEntityToInventory(wood);
        player.addEntityToInventory(arrow1);
        player.addEntityToInventory(arrow2);
        player.addEntityToInventory(arrow3);
        player.build("bow");
        List<ICollectableEntity> inventory = player.getInventory();
        for (ICollectableEntity item : inventory) {
            assertEquals(item.getType(), "bow");
        }
        assertTrue(player.getInventory().size() == 1);
    }

    @Test
    public void TestBuildShieldWithTreasure() {
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        WoodEntity wood2 = new WoodEntity();
        TreasureEntity treasure = new TreasureEntity();
        player.addEntityToInventory(wood);
        player.addEntityToInventory(wood2);
        player.addEntityToInventory(treasure);
        player.build("shield");
        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "shield");
        }
        assertTrue(player.getInventory().size() == 1);
    }

    @Test
    public void TestBuildShieldWithKey() {
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        WoodEntity wood2 = new WoodEntity();
        KeyEntity key = new KeyEntity(0,0,0,1);
        player.addEntityToInventory(wood);
        player.addEntityToInventory(wood2);
        player.addEntityToInventory(key);
        player.build("shield");
        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), "shield");
        }
        assertTrue(player.getInventory().size() == 1);
    }

    @Test
    public void TestBuildShieldHasBoth() {
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        WoodEntity wood2 = new WoodEntity();
        TreasureEntity treasure = new TreasureEntity();
        KeyEntity key = new KeyEntity(0,0,0,1);
        player.addEntityToInventory(wood);
        player.addEntityToInventory(wood2);
        player.addEntityToInventory(treasure);
        player.addEntityToInventory(key);
        player.build("shield");

        ShieldEntity shield = new ShieldEntity();
        List<IEntity> expected = new ArrayList<>();
        expected.add(key);
        expected.add(shield);

        int i = 0;
        List<ItemResponse> inventory = player.getInventoryInfo();
        for (ItemResponse item : inventory) {
            assertEquals(item.getType(), expected.get(i).getType());
            i++;
        }
        assertTrue(player.getInventory().size() == 2);
    }

    @Override
    @Test
    public void TestBattle() {
        // TODO Auto-generated method stub
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(character);
        entitiesControl.addEntity(zombie);
        zombie.battle(entitiesControl, character);
        assertEquals(58, character.getHealth());
        assertEquals(-2.0, zombie.getHealth());
    }

    @Test
    public void TestBattleAgainstTeam() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenaryEntity = new MercenaryEntity();
        character.addTeammates(mercenaryEntity);
        ZombieToastEntity zombie = new ZombieToastEntity();
        zombie.doBattle(character);
        assertTrue(zombie.getHealth() < 40);
    }

    @Test
    public void TestMercenaryJoinsTeam() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4, 0);
        TreasureEntity treasure = new TreasureEntity();       
        player.addEntityToInventory(treasure); 
        mercenary.interactWith(player);
        assertTrue(player.teammates.contains(mercenary));
        ZombieToastEntity zombie = new ZombieToastEntity();
        zombie.doBattle(player);
        assertTrue(zombie.getHealth() < 40);
    }

    @Test
    @Override
    public void testDropOneRing() {
        // null because when character dies, it does not need to drop ring
    }
}
