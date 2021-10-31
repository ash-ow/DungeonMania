package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
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
        entitiesControl.addEntity(mercenary);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(1, 0), mercenary.getPosition());
    }

    @Test
    public void TestCorrectMove() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(5, 5, 0);
        WallEntity wall = new WallEntity(4, 5, 0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(mercenary);
        entitiesControl.addEntity(wall);
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
        entitiesControl.addEntity(mercenary);
        entitiesControl.addEntity(wall);
        entitiesControl.addEntity(wall2);
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
        entitiesControl.addEntity(mercenary);
        mercenary.battle(entitiesControl, character);
        assertFalse(entitiesControl.contains(mercenary));        
    }

    @Test
    public void TestWalkInBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(1,0,0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(mercenary);
        assertEquals(100, character.getHealth());
        assertEquals(100, mercenary.getHealth());
        character.move(Direction.RIGHT, entitiesControl);
        assertFalse(entitiesControl.contains(mercenary));         
    }

    @Test
    public void TestBothWalkInBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(2,0,0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(mercenary);
        assertEquals(100, character.getHealth());
        assertEquals(100, mercenary.getHealth());
        character.move(Direction.RIGHT, entitiesControl);
        assertTrue(entitiesControl.contains(mercenary));     
        assertEquals(new Position(1, 0), character.getPosition());
        mercenary.move(entitiesControl, character);   
        assertEquals(new Position(1, 0), character.getPosition());
        assertEquals(new Position(1, 0), mercenary.getPosition());
        assertFalse(entitiesControl.contains(mercenary));            
    }
    
    @Test
    public void mercenaryMovesOutofWay() {
        CharacterEntity player = new CharacterEntity(0, 5, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4, 0);
        TreasureEntity treasure = new TreasureEntity();
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(mercenary);        
        player.addEntityToInventory(treasure); 
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.interact(mercenary.getId());
        assertTrue(mercenary.isBribed());
        dungeon.tick(Direction.UP);
        assertTrue(dungeon.entitiesControl.contains(mercenary));
        assertEquals(new Position(0, 5), mercenary.getPosition());
        dungeon.tick(Direction.DOWN);
        assertEquals(new Position(0, 4), mercenary.getPosition());
    }

    @Test
    public void runAway() {
        EntitiesControl entitiesControl = new EntitiesControl();
        CharacterEntity player = new CharacterEntity(5, 0, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 0, 0);
        entitiesControl.addEntity(mercenary);
        mercenary.move(entitiesControl, player);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(2, 0), mercenary.getPosition());
        mercenary.runAway(entitiesControl, player);
        assertEquals(new Position(1, 0), mercenary.getPosition());
        mercenary.runAway(entitiesControl, player);
        assertEquals(new Position(0, 0), mercenary.getPosition());
    }

    @Test
    public void testDropOneRing() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(0, 0, 0);
        mercenary.dropEntities(player, 1f);
        List<CollectableEntity> inventory = player.getInventory();
        assertNotNull(EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class));
    }
}
