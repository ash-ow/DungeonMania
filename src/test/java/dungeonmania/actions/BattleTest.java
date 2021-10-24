package dungeonmania.actions;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;

public class BattleTest {
    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        ZombieToastEntity zombie = new ZombieToastEntity();

        new Battle(character, zombie);
    }
    
}
