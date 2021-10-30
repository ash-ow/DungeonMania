package dungeonmania.entities.movingEntityTests;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.IInteractingEntityTest;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ZombieToastEntityTest implements IInteractingEntityTest, IMovingEntityTest, IEntityTests, IBattlingEntityTest {
    @Override
    @Test
    public void TestInteraction() {
        ZombieToastEntity zombie = new ZombieToastEntity();
        CharacterEntity character = new CharacterEntity(0, 1, 0);

        assertEquals(100, character.getHealth());
        assertEquals(100, zombie.getHealth());

        character.move(Direction.UP);
        assertEquals(zombie.getPosition(), character.getPosition());

        zombie.interactWithPlayer(new EntitiesControl(), character);
        assertEquals(58, character.getHealth());
        assertFalse(zombie.isAlive());
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

        zombie.doBattle(character);

        assertEquals(70, character.getHealth());
        assertEquals(40, zombie.getHealth());
    }

    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(zombie);
        zombie.battle(entitiesControl, character);
        assertFalse(entitiesControl.contains(zombie));
    }
}
