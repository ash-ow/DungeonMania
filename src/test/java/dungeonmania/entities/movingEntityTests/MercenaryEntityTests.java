package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntityTests implements IMovingEntityTest, IBattlingEntityTest{

    @Override
    @Test
    public void TestMove() {
        EntitiesControl entitiesControl = new EntitiesControl();
        CharacterEntity player = new CharacterEntity(5, 0, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 0, 0);
        entitiesControl.addEntities(mercenary);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(1, 0), mercenary.getPosition());
    }

    @Test
    public void TestCorrectMove() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(5, 5, 0);
        WallEntity wall = new WallEntity(4, 5, 0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(mercenary);
        entitiesControl.addEntities(wall);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(5, 4), mercenary.getPosition());
    }

    @Test
    public void TestBlocked() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(5, 5, 0);
        WallEntity wall = new WallEntity(4, 5, 0);
        WallEntity wall2 = new WallEntity(5, 4, 0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(mercenary);
        entitiesControl.addEntities(wall);
        entitiesControl.addEntities(wall2);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(5, 5), mercenary.getPosition());
    }

    @Override
    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity();

        assertEquals(100, character.getHealth());
        assertEquals(100, mercenary.getHealth());

        mercenary.doBattle(character);

        assertEquals(70, character.getHealth());
        assertEquals(40, mercenary.getHealth());
    }

    @Override
    @Test
    public void TestDeath() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity();
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(mercenary);
        mercenary.Battle(entitiesControl, character);
        assertFalse(entitiesControl.contains(mercenary));        
    }

    @Test
    public void TestWalkInBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(1,0,0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(mercenary);
        assertEquals(100, character.getHealth());
        assertEquals(100, mercenary.getHealth());
        character.moveCharacter(Direction.RIGHT, entitiesControl);
        assertFalse(entitiesControl.contains(mercenary));         
    }

    @Test
    public void TestBothWalkInBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(2,0,0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(mercenary);
        assertEquals(100, character.getHealth());
        assertEquals(100, mercenary.getHealth());
        character.moveCharacter(Direction.RIGHT, entitiesControl);
        assertTrue(entitiesControl.contains(mercenary));     
        assertEquals(new Position(1, 0), character.getPosition());
        mercenary.move(entitiesControl, character);   
        assertEquals(new Position(1, 0), character.getPosition());
        assertEquals(new Position(1, 0), mercenary.getPosition());
        assertFalse(entitiesControl.contains(mercenary));     
        
    }

}
