package dungeonmania.entities.movingEntityTests;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        // TODO The zombie toast is supposed to move randomly - this test does not reflect that
        ZombieToastEntity zombie = new ZombieToastEntity();
        assertPositionEquals(zombie.getPosition(), 0, 0);
        
        zombie.move(Direction.DOWN);
        assertPositionEquals(zombie.getPosition(), 0, 1);
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

        zombie.setDamage(5);
        character.setDamage(2);
        zombie.Battle(character);

        assertEquals(50, character.getHealth());
        assertEquals(60, zombie.getHealth());
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        
        zombie.setHealth(10);
        zombie.setDamage(5);
        character.setDamage(1);
        zombie.Battle(character);

        assertEquals(95, character.getHealth());
        assertEquals(-10, zombie.getHealth());
        // TODO add assertions for zombie death
    }
}
