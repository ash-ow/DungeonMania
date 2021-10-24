package dungeonmania.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;

public class BattleTest {
    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();

        assertEquals(100, character.getHealth());
        assertEquals(100, zombie.getHealth());

        zombie.Battle(character);

        assertEquals(90, character.getHealth());
        assertEquals(90, zombie.getHealth());
    }
    
}
