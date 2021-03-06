package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.WallEntity;
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
        CharacterEntity player = new CharacterEntity(0, 4);
        WallEntity wall = new WallEntity(0, 3);
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
        CharacterEntity player = new CharacterEntity(0, 4);
        assertEntityResponseInfoEquals(
            player,
            "player-0-4-0",
            EntityTypes.PLAYER,
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
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        player.getInventoryItems().add(wood);
        assertEquals(1, player.getInventoryItems().size());
        player.getInventoryItems().remove(wood);
        assertEquals(0, player.getInventoryItems().size());
    }

    @Test
    public void TestBuildBow() {
        CharacterEntity player = new CharacterEntity();
        WoodEntity wood = new WoodEntity();
        ArrowsEntity arrow1 = new ArrowsEntity();
        ArrowsEntity arrow2 = new ArrowsEntity();
        ArrowsEntity arrow3 = new ArrowsEntity();
        player.getInventoryItems().add(wood);
        player.getInventoryItems().add(arrow1);
        player.getInventoryItems().add(arrow2);
        player.getInventoryItems().add(arrow3);
        player.build(EntityTypes.BOW);
        List<ICollectable> inventory = player.getInventoryItems();
        for (ICollectable item : inventory) {
            assertEquals(item.getType(), EntityTypes.BOW);
        }
        assertEquals(1, player.getInventoryItems().size());
    }

    @Override
    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(character);
        entitiesControl.addEntity(zombie);
        zombie.battle(entitiesControl, character);
        assertEquals(85, character.getHealth());
        assertEquals(-10.0, zombie.getHealth());
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
        CharacterEntity player = new CharacterEntity(0, 5);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4);
        TreasureEntity treasure = new TreasureEntity();       
        player.getInventoryItems().add(treasure); 
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
