package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.dungeon.Instruction;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.Logic;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
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
        BombEntity bomb = new BombEntity(0, 1, Logic.ANY);
        BoulderEntity boulder = new BoulderEntity(0, 2);
        CharacterEntity player = new CharacterEntity(9, 9);
        ArrayList<Instruction> ticks = new ArrayList<>();
        ticks.add(new Instruction(Direction.DOWN));
        ticks.add(new Instruction(Direction.DOWN));
        ticks.add(new Instruction(bomb.getId()));
        ticks.add(new Instruction(Direction.DOWN));
        ticks.add(new Instruction(Direction.UP));
        OlderCharacter olderCharacter = new OlderCharacter(0, 0, GameModeType.STANDARD, ticks);
        entitiesControl.addEntity(bomb);
        entitiesControl.addEntity(boulder);
        entitiesControl.addEntity(olderCharacter);
        entitiesControl.moveAllMovingEntities(player);
        Assertions.assertAll(
            () -> assertFalse(entitiesControl.contains(bomb)),
            () -> assertNotNull(olderCharacter.getInventory().getInventoryItemById(bomb.getId())),
            () -> entitiesControl.moveAllMovingEntities(player),
            () -> assertEquals(olderCharacter.getPosition(), new Position(0, 2)),
            () -> assertEquals(olderCharacter.getPosition(), new Position(0, 2)),
            () -> assertEquals(boulder.getPosition(), new Position(0, 3)),
            () -> entitiesControl.moveAllMovingEntities(player),
            () -> assertTrue(entitiesControl.contains(bomb)),
            () -> entitiesControl.moveAllMovingEntities(player),
            () -> assertNotNull(olderCharacter.getInventory().getInventoryItemById(bomb.getId())),
            () -> entitiesControl.moveAllMovingEntities(player),
            () -> assertEquals(olderCharacter.getPosition(), new Position(0, 3)),
            () -> entitiesControl.moveAllMovingEntities(player),
            () -> assertEquals(olderCharacter.getPosition(), new Position(0, 3)),
            () -> entitiesControl.moveAllMovingEntities(player),
            () -> assertFalse(entitiesControl.contains(olderCharacter))
        );  
    }
}
