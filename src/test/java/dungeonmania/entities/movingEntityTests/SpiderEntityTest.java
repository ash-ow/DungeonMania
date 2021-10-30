package dungeonmania.entities.movingEntityTests;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        spider.interactWithPlayer(new EntitiesControl(), Direction.UP, character); // TODO I think this should be run automatically when positions are equal
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
            System.out.println(spider.getPosition());
            assertEquals(spider.getPosition(), expectPositions.get(i));
        }
    }
    

    @Test
    public void TestMoveHitBoulder() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.createEntity(6, 5, 0, "boulder");
        entities.addEntities(spider);

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(6, 4), new Position(6, 4),
            new Position(5, 4), new Position(4, 4), new Position(4, 5), new Position(4, 6), new Position(5, 6), new Position(6, 6),
            new Position(6, 6), new Position(5, 6), new Position(4, 6), new Position(4, 5), new Position(4, 4), new Position(5, 4),
            new Position(6, 4));
        
        for (int i = 0; i < expectPositions.size(); i++) {
            entities.moveAllMovingEntities(Direction.DOWN, player);
            System.out.println(spider.getPosition());
            assertEquals(spider.getPosition(), expectPositions.get(i));
        }
    }

    @Test
    public void TestMoveWall() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        SpiderEntity spider = new SpiderEntity(0, 4, 0);
        WallEntity wall = new WallEntity(0, 4, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(spider);
        entities.add(wall);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.tick(Direction.UP);

        assertEquals(player.getPosition(), new Position(0, 5));
    }

    @Test
    public void TestBoulderAboveSpiderSpawn() {
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        BoulderEntity boulder = new BoulderEntity(5, 4, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.addEntities(boulder);
        entities.addEntities(spider);



        List<Position> expectPositions = Arrays.asList(new Position(5, 5), new Position(5, 5), new Position(5, 5), new Position(5, 5));
        
        for (int i = 0; i < expectPositions.size(); i++) {
            entities.moveAllMovingEntities(Direction.DOWN, player);
            System.out.println(spider.getPosition());
            assertEquals(spider.getPosition(), expectPositions.get(i));
        }
    }

    @Test
    public void TestMovedBoulderAboveSpiderSpawn() {
        CharacterEntity player = new CharacterEntity(6, 4, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        BoulderEntity boulder = new BoulderEntity(5, 4, 0);
        EntitiesControl entities = new EntitiesControl();

        entities.addEntities(boulder);
        entities.addEntities(spider);

        entities.moveAllMovingEntities(Direction.DOWN, player);
        assertEquals(spider.getPosition(), new Position(5, 5));

        player.move(Direction.LEFT, entities);
        entities.moveAllMovingEntities(Direction.DOWN, player);
        assertEquals(spider.getPosition(), new Position(5, 4));
    }

    @Test
    public void TestSpiderTrapped() {
        CharacterEntity player = new CharacterEntity(6, 6, 0);
        SpiderEntity spider = new SpiderEntity(5, 5, 0);
        BoulderEntity boulder1 = new BoulderEntity(4, 4, 0);
        BoulderEntity boulder2 = new BoulderEntity(6, 4, 0);

        EntitiesControl entities = new EntitiesControl();

        entities.addEntities(player);
        entities.addEntities(spider);
        entities.addEntities(boulder1);
        entities.addEntities(boulder2);

        List<Position> expectPositions = Arrays.asList(new Position(5, 4), new Position(5, 4), new Position(5, 4), new Position(5, 4), new Position(5, 4), new Position(5, 4));
        
        for (Position expectPosition : expectPositions) {
            entities.moveAllMovingEntities(Direction.DOWN, player);
            System.out.println(spider.getPosition());
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
            "spider",
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
        entitiesControl.addEntities(spider);
        spider.Battle(entitiesControl, character);
        assertFalse(entitiesControl.contains(spider));
        // TODO add assertions for spider death
    }
}