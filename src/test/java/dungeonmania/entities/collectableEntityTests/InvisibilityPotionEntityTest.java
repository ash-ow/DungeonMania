package dungeonmania.entities.collectableEntityTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvisibilityPotionEntity;
import dungeonmania.entities.movingEntities.spiderEntity.*;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.dungeon.*;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class  InvisibilityPotionEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        InvisibilityPotionEntity  invisibility_potion = new  InvisibilityPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            invisibility_potion,
            "invisibility_potion-0-0-0",
            "invisibility_potion",
            new Position(0,0),
            false
        );
    }

    @Test
    @Override
    public void TestCollect() {
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0,0);
        assertEntityIsCollected(invisibility_potion);
    }

    
   
    @Test
    @Override 
    public void TestUseCollectable() {
        CharacterEntity player = new CharacterEntity(0,0,0);
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0,0);
        invisibility_potion.used(player);
        assertTrue(player.isInvisible());
    }

    

    @Test
    public void TestInteract() {
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        SpiderEntity spider = new SpiderEntity(0,1,0);
        WoodEntity wood = new WoodEntity(0,2,0);
        ArrayList<IEntity> entities = new ArrayList<>();
        
        entities.add(spider);
        entities.add(invisibility_potion);
        entities.add(wood);

        invisibility_potion.used(player);
       
        //player avoids battle with spider and both have full health
        player.move(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 1, 0), spider.getPosition());
        assertTrue(player.isInvisible());
        assertEquals(100, player.getHealth());
        assertEquals(100, spider.getHealth());

        //Player can pick up wood while invisible 
        player.move(Direction.DOWN);
        assertTrue(player.isInvisible());
        assertEquals(new Position(0, 2, 0), player.getPosition());
        assertEquals(new Position(0, 2, 0), wood.getPosition());
        assertEntityIsCollected(wood);
        


    }
    /* TO DO: Fix Duration && redo test 
    @Test
    public void TestDuration() {
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        invisibility_potion.used(player);

        //test duration
			//player.move(Direction.DOWN);
            
            //assertTrue(player.isInvisible());

            //player.move(Direction.DOWN);
            //assertEquals(player.getDuration(), 8);
            //assertTrue(player.isInvisible());
        
            for (int i=1;i<10;i++) {
            player.move(Direction.DOWN);
            }
            assertEquals(player.getDuration(), 0);
            assertFalse(player.isInvisible());
	
        }
        */
    
    

}