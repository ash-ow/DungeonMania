package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.dungeon.Instruction;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.OlderCharacter;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class OlderCharacterTest implements IEntityTests {

    @Override
    @Test
    public void TestEntityResponseInfo() {
        OlderCharacter player = new OlderCharacter(0, 4, GameModeType.STANDARD, new ArrayList<>());
        assertEntityResponseInfoEquals(
            player,
            "older_player-0-4-0",
            EntityTypes.OLDER_PLAYER,
            new Position(0,4),
            false
        );
    }
    
    @Test
    public void TestOlderCharacterMove() {
        EntitiesControl entitiesControl = new EntitiesControl();
        BombEntity bomb = new BombEntity(0, 1);
        BoulderEntity boulder = new BoulderEntity(0, 2);
        List<Instruction> ticks = new ArrayList<>();
        ticks.add(new Instruction(Direction.DOWN));
        ticks.add(new Instruction(Direction.DOWN));
        ticks.add(new Instruction(bomb.getId()));
        ticks.add(new Instruction(Direction.DOWN));
        ticks.add(new Instruction(Direction.UP));
        OlderCharacter olderCharacter = new OlderCharacter(0, 0, GameModeType.STANDARD, ticks);
        entitiesControl.addEntity(bomb);
        entitiesControl.addEntity(boulder);
        entitiesControl.addEntity(olderCharacter);
        entitiesControl.tick();
        assertFalse(entitiesControl.contains(bomb));
        assertTrue(olderCharacter.getInventory().contains(bomb));
        entitiesControl.tick();
        assertEquals(olderCharacter.getPosition(), new Position(0, 2));
        assertEquals(boulder.getPosition(), new Position(0, 3));
        entitiesControl.tick();
        assertTrue(entitiesControl.contains(bomb));
        assertFalse(olderCharacter.getInventory().contains(bomb));
        entitiesControl.tick();
        assertEquals(olderCharacter.getPosition(), new Position(0, 3));
        entitiesControl.tick();
        assertEquals(olderCharacter.getPosition(), new Position(0, 3));
        entitiesControl.tick();
        assertFalse(entitiesControl.contains(olderCharacter));
    }

}
