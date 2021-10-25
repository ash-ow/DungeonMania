package dungeonmania.entities.movingEntityTests;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.SpiderEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        spider.interactWithPlayer(new EntitiesControl(), Direction.DOWN, character); // TODO I think this should be run automatically when positions are equal
        assertEquals(new Position(0,1), character.getPosition());
    }

    @Override
    @Test
    public void TestMove() {
        // TODO The spider is supposed to loop around its spawn location - this test does not reflect that
        IMovingEntity spider = new SpiderEntity();
        assertPositionEquals(spider.getPosition(), 0, 0);
        
        spider.move(Direction.DOWN);
        assertPositionEquals(spider.getPosition(), 0, 1);
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

        spider.Battle(character);

        assertEquals(80, character.getHealth());
        assertEquals(40, spider.getHealth());
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        SpiderEntity spider = new SpiderEntity();
        
        spider.setHealth(2);
        spider.Battle(character);

        assertEquals(99.6, character.getHealth(), 0.1);
        assertEquals(-58, spider.getHealth());

        // TODO add assertions for spider death
    }
}