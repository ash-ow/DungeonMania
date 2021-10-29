package dungeonmania.entities.movingEntityTests;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.entities.staticEntityTest.WallEntityTest;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ZombieToastEntityTest implements IInteractingEntityTest, IMovingEntityTest, IEntityTests, IBattlingEntityTest {
    @Override
    @Test
    public void TestInteraction() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity character = new CharacterEntity(0, 1, 0);

        character.move(Direction.UP);
        assertEquals(zombie.getPosition(), character.getPosition());
        
        // TODO This is only testing the stub in the ZombieToastEntity class - not the actual interaction between the two
        zombie.interactWithPlayer(new EntitiesControl(), Direction.DOWN, character); // TODO I think this should be run automatically when positions are equal
        assertEquals(new Position(0,1), zombie.getPosition());
    }

    @Override
    @Test
    public void TestMove() {
        ZombieToastEntity zombie = new ZombieToastEntity(5, 5, 0, 10);
        EntitiesControl entities = new EntitiesControl();
        entities.addEntities(zombie);
        entities.createEntity(7, 5, 0, "wall");

        List<Position> expectPositions = Arrays.asList(new Position(6, 5), new Position(6, 5));

        for (Position expectPosition : expectPositions) {
            zombie.move(Direction.DOWN, entities);
            assertEquals(zombie.getPosition(), expectPosition);
        }
    }

    @Override
    @Test
    public void TestEntityResponseInfo() {
        ZombieToastEntity zombie = new ZombieToastEntity(0, 0, 0);
        assertEntityResponseInfoEquals(
            zombie,
            "zombieToast-0-0-0",
            "zombieToast",
            new Position(0,0),
            false
        );
    }

    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();

        assertEquals(100, character.getHealth());
        assertEquals(100, zombie.getHealth());

        zombie.Battle(character);

        assertEquals(70, character.getHealth());
        assertEquals(40, zombie.getHealth());
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        
        zombie.setHealth(2);
        zombie.Battle(character);

        assertEquals(99.4, character.getHealth(), 0.1);
        assertEquals(-58, zombie.getHealth());

        // TODO add assertions for zombie death
    }
}
