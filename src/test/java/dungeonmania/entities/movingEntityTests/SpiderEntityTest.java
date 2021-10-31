package dungeonmania.entities.movingEntityTests;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SpiderEntityTest implements IInteractingEntityTest, IMovingEntityTest, IEntityTests, IBattlingEntityTest {
    @Override
    @Test
    public void TestInteraction() {
        SpiderEntity spider = new SpiderEntity();
        CharacterEntity character = new CharacterEntity(0, 1, 0);

        character.move(Direction.UP);
        assertEquals(spider.getPosition(), character.getPosition());
        
        // TODO This is only testing the stub in the SpiderEntity class - not the actual interaction between the two
        spider.contactWithPlayer(new EntitiesControl(), character); // TODO I think this should be run automatically when positions are equal
        assertEquals(new Position(0,0), character.getPosition());
    }

    @Override
    @Test
    public void TestMove() {
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        assertPositionEquals(spider.getPosition(), 5, 5);

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(6, 4), new Position(6, 5),
            new Position(6, 6), new Position(5, 6), new Position(4, 6), new Position(4, 5), new Position(4, 4), new Position(5, 4));
        
        for (int i = 0; i < expectPositions.size(); i++) {
            spider.move(new EntitiesControl(), new CharacterEntity());
            assertEquals(spider.getPosition(), expectPositions.get(i));
        }
    }

    @Test
    public void TestMoveHitBoulder() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.createEntity(6, 5, 0, EntityTypes.BOULDER);
        entities.addEntity(spider);

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(6, 4), new Position(6, 4),
            new Position(5, 4), new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(5, 6), new Position(6, 6),
            new Position(6, 6), new Position(5, 6), new Position(4, 6), new Position(4, 5), new Position(4, 4), new Position(5, 4),
            new Position(6, 4));
        
        for (int i = 0; i < expectPositions.size(); i++) {
            entities.moveAllMovingEntities(player);
            assertEquals(spider.getPosition(), expectPositions.get(i));
        }
    }

    @Test
    public void TestMoveWall() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        SpiderEntity spider = new SpiderEntity(0, 4, 0);
        WallEntity wall = new WallEntity(0, 4, 1);
        WallEntity wall2 = new WallEntity(0, 3, 0);
        WallEntity wall3 = new WallEntity(1, 3, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);
        entities.add(wall);
        entities.add(wall2);
        entities.add(wall3);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.tick(Direction.UP);
        assertEquals(new Position(0, 3), spider.getPosition());
        dungeon.tick(Direction.UP);
        assertEquals(new Position(1, 3), spider.getPosition());
        dungeon.tick(Direction.UP);

    }

    @Test
    public void TestBoulderAboveSpiderSpawn() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        BoulderEntity boulder = new BoulderEntity(5, 4, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.addEntity(boulder);
        entities.addEntity(spider);

        List<Position> expectPositions = Arrays.asList(new Position(5, 5), new Position(5, 5), new Position(5, 5), new Position(5, 5));
        
        for (int i = 0; i < expectPositions.size(); i++) {
            entities.moveAllMovingEntities(player);
            assertEquals(spider.getPosition(), expectPositions.get(i));
        }
    }

    @Test
    public void TestMovedBoulderAboveSpiderSpawn() {
        CharacterEntity player = new CharacterEntity(6, 4, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        BoulderEntity boulder = new BoulderEntity(5, 4, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.addEntity(boulder);
        entities.addEntity(spider);

        entities.moveAllMovingEntities(player);
        assertEquals(spider.getPosition(), new Position(5, 5));

        player.move(Direction.LEFT, entities);
        entities.moveAllMovingEntities(player);
        assertEquals(spider.getPosition(), new Position(5, 4));
    }

    @Test
    public void TestSpiderTrapped() {
        CharacterEntity player = new CharacterEntity(6, 6, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        BoulderEntity boulder1 = new BoulderEntity(4, 4, 0);
        BoulderEntity boulder2 = new BoulderEntity(6, 4, 0);

        EntitiesControl entities = new EntitiesControl();

        entities.addEntity(player);
        entities.addEntity(spider);
        entities.addEntity(boulder1);
        entities.addEntity(boulder2);

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(5, 4), new Position(5, 4), new Position(5, 4), new Position(5, 4), new Position(5, 4));
        
        for (Position expectPosition : expectPositions) {
            entities.moveAllMovingEntities(player);
            assertEquals(spider.getPosition(), expectPosition);
        }
    }


    @Override
    @Test
    public void TestEntityResponseInfo() {
        SpiderEntity spider = new SpiderEntity(0, 0, 0);
        assertEntityResponseInfoEquals(
            spider,
            "spider-0-0-0",
            EntityTypes.SPIDER,
            new Position(0,0),
            false
        );
    }

    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        SpiderEntity spider = new SpiderEntity();

        assertEquals(100, character.getHealth());
        assertEquals(100, spider.getHealth());

        spider.doBattle(character);

        assertEquals(80, character.getHealth());
        assertEquals(40, spider.getHealth());
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        SpiderEntity spider = new SpiderEntity();
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(spider);
        spider.battle(entitiesControl, character);
        assertFalse(entitiesControl.contains(spider));
        // TODO add assertions for spider death
    }

    @Test
    @Override
    public void testDropOneRing() {
        CharacterEntity player = new CharacterEntity();
        SpiderEntity spider = new SpiderEntity(0, 0, 0);
        spider.dropEntities(player, 1f);
        List<ICollectableEntity> inventory = player.getInventory();
        assertNotNull(EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class));
    }
}