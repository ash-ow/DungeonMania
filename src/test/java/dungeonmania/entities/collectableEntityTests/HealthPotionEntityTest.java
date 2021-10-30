package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.HealthPotionEntity;
import dungeonmania.entities.movingEntities.spiderEntity.*;

import dungeonmania.util.Direction;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.response.models.ItemResponse;


public class HealthPotionEntityTest implements ICollectableEntityTest {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        assertEntityResponseInfoEquals(
            health_potion,
            "health_potion-0-0-0",
            "health_potion",
            new Position(0,0),
            false
        );
    }

    @Test

    @Override
    public void TestCollect() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        assertEntityIsCollected(health_potion);
    }

    @Test
    public void TestUseCollectable() {
        HealthPotionEntity health_potion = new HealthPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        
        player.setHealth(40);
        health_potion.used(player);
        assertEquals(100, player.getHealth());
}

    @Test
    public void TestPotionBattle() {
    HealthPotionEntity health_potion = new HealthPotionEntity();
    CharacterEntity character = new CharacterEntity();
    SpiderEntity spider = new SpiderEntity();

    assertEquals(100, character.getHealth());
    
    spider.doBattle(character);
    assertEquals(80, character.getHealth());
    assertEquals(40, spider.getHealth());

    health_potion.used(character);
    assertEquals(100, character.getHealth());
    assertEquals(40, spider.getHealth());

}

}
