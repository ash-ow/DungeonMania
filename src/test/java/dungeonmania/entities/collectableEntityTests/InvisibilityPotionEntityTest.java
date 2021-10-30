package dungeonmania.entities.collectableEntityTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvisibilityPotionEntity;
import dungeonmania.entities.movingEntities.spiderEntity.*;
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

    @Override
   
    @Test
    public void TestUseCollectable() {
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        SpiderEntity spider = new SpiderEntity(0,1,0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(player);
        entitiesControl.addEntities(spider);
        entitiesControl.addEntities(invisibility_potion);

        assertEquals(new Position(0, 0, 0), player.getPosition());
        assertEquals(new Position(0, 1, 0), spider.getPosition());

        invisibility_potion.used(player);
        player.move(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 1, 0), spider.getPosition());

        player.move(Direction.DOWN);
        assertEquals(new Position(0, 2, 0), player.getPosition());
        assertEquals(new Position(0, 1, 0), spider.getPosition());

        assertEquals(0, player.getBattleCount());
        assertEquals(100, player.getHealth());
        assertEquals(100, spider.getHealth());
    }

    /*TO DO: wait for wilson branch to run the findcollectablebyID test
        @Test
        public void TestDuration() {
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity();
        CharacterEntity player = new CharacterEntity();
        SpiderEntity spider = new SpiderEntity();
        ArrayList<IEntity> entities = new ArrayList<>();  

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        
            
        invisibility_potion.used(player);

        //TO DO: test battle/interaction
        assertEquals(100, player.getDamage());
        //TO DO: test enemy moving away

        //test duration
        for (int i = 0; i < 9; i++) {
			assertTrue(player.getDuration(), 10 - i);
			dungeon.tick(Direction.DOWN);
            assertNotNull(player.findCollectableById(invisibility_potion.getId()), "Inventory should contain entity " + invisibility_potion.getId());
        }
		dungeon.tick(Direction.DOWN);
		assertNULL(player.findCollectableById(invisibility_potion.getId()), "Inventory should not contain entity " + invisibility_potion.getId());
	}
    */
    
    

}