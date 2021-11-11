package dungeonmania.entities.collectableEntityTests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvisibilityPotionEntity;
import dungeonmania.entities.movingEntities.spiderEntity.*;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.dungeon.*;

import dungeonmania.entities.IEntity;
import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;

import dungeonmania.entities.EntityTypes;

public class  InvisibilityPotionEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        InvisibilityPotionEntity  invisibility_potion = new  InvisibilityPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            invisibility_potion,
            "invisibility_potion-0-0-0",
            EntityTypes.INVISIBILITY_POTION,
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
    public void TestAvoidBattle() {
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        SpiderEntity spider = new SpiderEntity(0,1,0);
        ArrayList<IEntity> entities = new ArrayList<>();
        
        entities.add(spider);
        entities.add(invisibility_potion);

        invisibility_potion.used(player);
       
        //player avoids battle with spider and both have full health
        player.move(Direction.DOWN);
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 1, 0), spider.getPosition());
        assertTrue(player.isInvisible());
        assertEquals(100, player.getHealth());
        assertEquals(35, spider.getHealth());

    }

    @Test
    public void TestCanStillCollect() {
        CharacterEntity player = new CharacterEntity(0,0,0);
        ArrayList<IEntity> entities = new ArrayList<>();
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0,0);
        WoodEntity wood = new WoodEntity(0,1,0);
        entities.add(invisibility_potion);
        entities.add(wood);
        
        invisibility_potion.used(player);
        //Player can pick up wood while invisible 
        player.move(Direction.DOWN);
        assertTrue(player.isInvisible());
        assertEquals(new Position(0, 1, 0), player.getPosition());
        assertEquals(new Position(0, 1, 0), wood.getPosition());
        assertEntityIsCollected(wood);
    }

    @Test
    public void TestInvisibilityRunsOut() {
        ArrayList<IEntity> entities = new ArrayList<>();
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,1,0);
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        Dungeon dungeon = new Dungeon(entities, "standard", player);
        entities.add(invisibility_potion);
        
        dungeon.tick(Direction.DOWN);
        assertItemInInventory("invisibility_potion-0-1-0", player, dungeon.entitiesControl);
        assertEquals(new Position(0, 1, 0), player.getPosition());

        invisibility_potion.used(player);
        assertItemNotInInventory("invisibility_potion-0-1-0", player, dungeon.entitiesControl, false);
        assertTrue(player.isInvisible());

        for (int i = 0; i < 11;i++) {
            dungeon.tick(Direction.DOWN);
        }
        
        assertFalse(player.isInvisible());
        assertItemNotInInventory("invincibility_potion-0-0-0", player, dungeon.entitiesControl, false);
    }
}