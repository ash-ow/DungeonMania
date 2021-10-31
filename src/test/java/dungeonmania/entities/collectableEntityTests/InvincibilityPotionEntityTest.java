package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.dungeon.*;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvincibilityPotionEntity;
import dungeonmania.entities.movingEntities.spiderEntity.*;
import dungeonmania.entities.movingEntities.MercenaryEntity;

import dungeonmania.entities.IEntityTests;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class InvincibilityPotionEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            invincibility_potion,
            "invincibility_potion-0-0-0",
            "invincibility_potion",
            new Position(0,0),
            false
        );
    }
    @Test
    @Override
    public void TestCollect() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        assertEntityIsCollected(invincibility_potion);
        
    }

    @Test
    @Override
    public void TestUseCollectable() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        invincibility_potion.used(player);
        assertTrue(player.isInvincible());
    }

    @Test
    public void TestUseBattle() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        SpiderEntity spider = new SpiderEntity(0,1,0);
        ArrayList<IEntity> entities = new ArrayList<>();
    
        entities.add(spider);
        entities.add(invincibility_potion);
        
        invincibility_potion.used(player);
        player.move(Direction.DOWN);
        spider.doBattle(player);
        assertEquals(100, player.getHealth());
        assertEquals(0, spider.getHealth());
        assertFalse(spider.isAlive()); 
    }

    @Test
    public void TestInvincibilityRunsOut() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        invincibility_potion.used(player);
        assertTrue(player.isInvincible());

        // Add a dungeon or otherwise make the player move 10 times 

        assertFalse(player.isInvincible());

        // Repeat for the invis potion
    }

    /*
    @Test
    public void TestMultipleBattle() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        SpiderEntity spider = new SpiderEntity(0,1,0);
        MercenaryEntity mercenary = new MercenaryEntity(0,2,0);

        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(player);
        entitiesControl.addEntities(spider);
        entitiesControl.addEntities(invincibility_potion);
        entitiesControl.addEntities(mercenary);
        
        invincibility_potion.used(player);
        //battle one
        player.move(Direction.DOWN);
        spider.doBattle(player);
        assertEquals(0, spider.getHealth());
        assertFalse(spider.isAlive()); 

        //battle two
        player.move(Direction.DOWN);
        mercenary.doBattle(player);
        assertTrue(mercenary.isAlive()); 
    }

    
    @Test
    public void TestDuration() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        SpiderEntity spider = new SpiderEntity(0,0,0);
        ArrayList<IEntity> entities = new ArrayList<>();  
        entities.add(player);
        entities.add(invincibility_potion);
        entities.add(spider);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
            
        invincibility_potion.used(player);
        //test duratiom
        for (int i = 0; i < 9; i++) {
            assertEquals(player.getDuration(), 10 - i);
            dungeon.tick(Direction.DOWN);
            assertNotNull(player.findCollectableById(invincibility_potion.getId()), "Inventory should contain entity " + invincibility_potion.getId());
            }
            dungeon.tick(Direction.DOWN);
            assertNULL(player.findCollectableById(invincibility_potion.getId()), "Inventory should not contain entity " + invincibility_potion.getId());
        }//test duration
    }
    */
    
}