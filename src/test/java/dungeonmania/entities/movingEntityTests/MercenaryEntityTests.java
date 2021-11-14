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
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.collectableEntities.InvincibilityPotionEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.entities.collectableEntities.TreasureEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.moveBehaviour.RunAway;
import dungeonmania.entities.staticEntities.SwampEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntityTests implements IMovingEntityTest, IBattlingEntityTest{

    @Override
    @Test
    public void TestMove() {
        EntitiesControl entitiesControl = new EntitiesControl();
        CharacterEntity player = new CharacterEntity(5, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 0);
        entitiesControl.addEntity(mercenary);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(1, 0), mercenary.getPosition());
    }

    @Test
    public void TestSpawn() {
        CharacterEntity player = new CharacterEntity(0, 1);
        MercenaryEntity mercenary = new MercenaryEntity(0, 0);
        ArrayList<IEntity> entities = new ArrayList<IEntity>();
        entities.add(mercenary);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        for (int i = 0; i < 30; i++) {
            dungeon.tick(Direction.DOWN);
        }
        assertEquals(2, dungeon.entitiesControl.getEntitiesOfType(MercenaryEntity.class).size());
    }

    @Test
    public void TestCorrectMove() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(5, 5);
        WallEntity wall = new WallEntity(4, 5);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(mercenary);
        entitiesControl.addEntity(wall);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(5, 4), mercenary.getPosition());
    }

    @Test
    public void TestBlocked() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(5, 5);
        WallEntity wall = new WallEntity(4, 5);
        WallEntity wall2 = new WallEntity(5, 4);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(mercenary);
        entitiesControl.addEntity(wall);
        entitiesControl.addEntity(wall2);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(5, 6), mercenary.getPosition());
    }

    @Override
    @Test
    public void TestBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity();

        assertEquals(100, character.getHealth());
        assertEquals(70, mercenary.getHealth());

        mercenary.doBattle(character);

        assertEquals(79, character.getHealth());
        assertEquals(10.0, mercenary.getHealth());
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
        assertEquals(76, character.getHealth());     
    }

    @Test
    public void TestWalkInBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(1, 0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(mercenary);
        assertEquals(100, character.getHealth());
        assertEquals(70, mercenary.getHealth());
        character.move(Direction.RIGHT, entitiesControl);
        assertFalse(entitiesControl.contains(mercenary));         
    }

    @Test
    public void TestBothWalkInBattle() {
        CharacterEntity character = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(2, 0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntity(mercenary);
        assertEquals(100, character.getHealth());
        assertEquals(70, mercenary.getHealth());
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
        CharacterEntity player = new CharacterEntity(0, 5);
        MercenaryEntity mercenary = new MercenaryEntity(0, 4);
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
        CharacterEntity player = new CharacterEntity(5, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 0);
        entitiesControl.addEntity(mercenary);
        mercenary.move(entitiesControl, player);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(2, 0), mercenary.getPosition());
        mercenary.setMoveBehaviour(new RunAway());
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(1, 0), mercenary.getPosition());
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(0, 0), mercenary.getPosition());
    }

    @Test
    public void doesntRunAway() {
        EntitiesControl entitiesControl = new EntitiesControl();
        InvincibilityPotionEntity potion = new InvincibilityPotionEntity();       
        CharacterEntity player = new CharacterEntity(5, 0, GameModeType.HARD);
        MercenaryEntity mercenary = new MercenaryEntity(0, 0);
        entitiesControl.addEntity(mercenary);
        entitiesControl.addEntity(potion);
        potion.contactWithPlayer(entitiesControl, player);
        player.useItem(potion.getId(), entitiesControl);
        entitiesControl.moveAllMovingEntities(player);
        assertEquals(new Position(1, 0), mercenary.getPosition());
        entitiesControl.moveAllMovingEntities(player);
        assertEquals(new Position(2, 0), mercenary.getPosition());
    }

    @Test
    public void testDropOneRing() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(0, 0);
        mercenary.dropEntities(player, 1f);
        List<ICollectable> inventory = player.getInventory();
        assertNotNull(EntitiesControl.getFirstEntityOfType(inventory, OneRingEntity.class));
    }

    @Test
    public void TestBestMove() {
        EntitiesControl entitiesControl = new EntitiesControl();
        CharacterEntity player = new CharacterEntity(11, 7);
        MercenaryEntity mercenary = new MercenaryEntity(11, 5);
        WallEntity wall1 = new WallEntity(11, 6);
        WallEntity wall2 = new WallEntity(12, 6);
        WallEntity wall3 = new WallEntity(10, 6);
        WallEntity wall4 = new WallEntity(10, 5);
        WallEntity wall5 = new WallEntity(10, 4);
        WallEntity wall6 = new WallEntity(10, 3);
        
        entitiesControl.addEntity(player);
        entitiesControl.addEntity(mercenary);
        entitiesControl.addEntity(wall1);
        entitiesControl.addEntity(wall2);
        entitiesControl.addEntity(wall3);
        entitiesControl.addEntity(wall4);
        entitiesControl.addEntity(wall5);
        entitiesControl.addEntity(wall6);

        
        player.move(Direction.LEFT);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(12, 5), mercenary.getPosition());

        player.move(Direction.LEFT);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(13, 5), mercenary.getPosition());

        player.move(Direction.UP);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(13, 6), mercenary.getPosition());
    }

    @Test
    public void TestNoBestMove() {
        EntitiesControl entitiesControl = new EntitiesControl();
        CharacterEntity player = new CharacterEntity(11, 7);
        MercenaryEntity mercenary = new MercenaryEntity(11, 5);
        WallEntity wall1 = new WallEntity(11, 6);
        WallEntity wall2 = new WallEntity(11, 8);
        WallEntity wall3 = new WallEntity(10, 7);
        WallEntity wall4 = new WallEntity(12, 7);
        
        entitiesControl.addEntity(player);
        entitiesControl.addEntity(mercenary);
        entitiesControl.addEntity(wall1);
        entitiesControl.addEntity(wall2);
        entitiesControl.addEntity(wall3);
        entitiesControl.addEntity(wall4);

        mercenary.move(entitiesControl, player);
        assertEquals(new Position(11, 5), mercenary.getPosition());
    }

    @Test
    public void TestBestMoveThroughSwamp() {
        ArrayList<IEntity> entities = new ArrayList<>();

        // mercenary will try to move down to follow the player, and must go through the swamp tile as that will be the shortest path
        MercenaryEntity merc = new MercenaryEntity();
        entities.add(merc);
        SwampEntity swamp = new SwampEntity(0, 1);
        entities.add(swamp);
        WallEntity wall1 = new WallEntity(1,1);
        entities.add(wall1);
        WallEntity wall2 = new WallEntity(2,1);
        entities.add(wall2);
        CharacterEntity player = new CharacterEntity(0, 3);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);

        // mercenary moves down into swamp and is stuck for 3 ticks
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 1));
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 1));
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 1));
        
        // mercenary is finally able to move down to follow the player
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(0, 2));
    }

    @Test
    public void TestBestMoveAroundSwamp() {
        ArrayList<IEntity> entities = new ArrayList<>();
        // mercenary will move around the swamp tiles as that will be the shortest path
        MercenaryEntity merc = new MercenaryEntity();
        entities.add(merc);
        SwampEntity swamp1 = new SwampEntity(0, 1);
        entities.add(swamp1);
        SwampEntity swamp2 = new SwampEntity(0, 2);
        entities.add(swamp2);
        SwampEntity swamp3 = new SwampEntity(0, 3);
        entities.add(swamp3);
        CharacterEntity player = new CharacterEntity(0, 4);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);

        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(1, 0));
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(1, 1));
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(1, 2));
        dungeon.tick(Direction.DOWN);
        assertEquals(merc.getPosition(), new Position(1, 3));
    }

    @Test
    public void TestPlayerIsInSwamp() {
        ArrayList<IEntity> entities = new ArrayList<>();

        // mercenary move down into the swamp tile, as that will be where the player is
        MercenaryEntity merc = new MercenaryEntity();
        entities.add(merc);
        SwampEntity swamp = new SwampEntity(0, 1);
        entities.add(swamp);
        CharacterEntity player = new CharacterEntity(0, 1);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        dungeon.tick(Direction.NONE);
        
        // Mercenary should be in the same position as the player, however, in this case, the mercenary should not be in the list of entities as they would have battled
        assertFalse(entities.contains(merc));
    }

    @Test
    public void TestSimpleSwamp() {
        ArrayList<IEntity> entities = new ArrayList<>();

        MercenaryEntity merc = new MercenaryEntity();
        entities.add(merc);
        SwampEntity swamp = new SwampEntity(0, 1);
        entities.add(swamp);
        SwampEntity swamp2 = new SwampEntity(1, 1);
        entities.add(swamp2);
        CharacterEntity player = new CharacterEntity(2, 1);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        
        dungeon.tick(Direction.NONE);
        assertEquals(merc.getPosition(), new Position(1, 0));
        dungeon.tick(Direction.NONE);
        assertEquals(merc.getPosition(), new Position(2, 0));
        dungeon.tick(Direction.NONE);
        assertFalse(entities.contains(merc));
    }
}
