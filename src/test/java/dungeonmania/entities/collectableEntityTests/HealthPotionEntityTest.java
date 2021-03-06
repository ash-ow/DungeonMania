package dungeonmania.entities.collectableEntityTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.collectableEntities.HealthPotionEntity;
import dungeonmania.entities.collectableEntities.InvisibilityPotionEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.spiderEntity.*;


public class HealthPotionEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0);
        assertEntityResponseInfoEquals(
            health_potion,
            "health_potion-0-0-0",
            EntityTypes.HEALTH_POTION,
            new Position(0,0),
            false
        );
    }

    @Test

    @Override
    public void TestCollect() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0);
        assertEntityIsCollected(health_potion);
    }

    @Test
    public void TestUseCollectable() {
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0);
        HealthPotionEntity health_potion = new HealthPotionEntity(0,1);
        entities.add(health_potion);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        
        dungeon.tick(Direction.DOWN);
        assertItemInInventory("health_potion-0-1-0", player, dungeon.entitiesControl);
        assertEquals(new Position(0, 1, 0), player.getPosition());

        player.setHealth(40);
        dungeon.tick("health_potion-0-1-0");
        health_potion.used(player);
        assertEquals(100, player.getHealth());
        assertItemNotInInventory("health_potion-0-1-0", player, dungeon.entitiesControl, false);
    }

    @Test
    public void TestPotionBattle() {
    HealthPotionEntity health_potion = new HealthPotionEntity();
    CharacterEntity character = new CharacterEntity();
    SpiderEntity spider = new SpiderEntity();

    assertEquals(100, character.getHealth());
    
    spider.doBattle(character);
    assertEquals(93.0, character.getHealth());
    assertEquals(-25.0, spider.getHealth());

    health_potion.used(character);
    assertEquals(100, character.getHealth());
    assertEquals(-25.0, spider.getHealth());
    }

    @Test
    public void TestTwoPotions() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0);
        CharacterEntity player = new CharacterEntity(0, 0);
        SpiderEntity spider = new SpiderEntity(0,1);
        InvisibilityPotionEntity invisibility_potion = new InvisibilityPotionEntity(0,0); 
        ArrayList<IEntity> entities = new ArrayList<>();

        entities.add(health_potion);
        entities.add(spider);
        entities.add(invisibility_potion);

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        
        player.setHealth(40);
        invisibility_potion.used(player);
        assertTrue(player.isInvisible());

        dungeon.tick(Direction.DOWN);
        assertEquals(40, player.getHealth());
        assertEquals(35, spider.getHealth());

        dungeon.tick(Direction.DOWN);
        health_potion.used(player);
        assertEquals(100, player.getHealth());  
    }

}





