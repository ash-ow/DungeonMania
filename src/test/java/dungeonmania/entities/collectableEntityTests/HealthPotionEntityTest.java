package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.IEntity;
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
        ArrayList<IEntity> entities = new ArrayList<>();
        CharacterEntity player = new CharacterEntity(0, 0, 0);
        HealthPotionEntity health_potion = new HealthPotionEntity(0,1,0);
        entities.add(health_potion);
        Dungeon dungeon = new Dungeon(entities, "Standard", player);
        
        dungeon.tick(Direction.DOWN);
        assertItemInInventory("health_potion-0-1-0", player, dungeon.entitiesControl);
        assertEquals(new Position(0, 1, 0), player.getPosition());

        player.setHealth(40);
        dungeon.tick("health_potion-0-1-0");
        assertEquals(100, player.getHealth());
        assertItemNotInInventory("health_potion-0-1-0", player, dungeon.entitiesControl);
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
