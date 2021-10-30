package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvincibilityPotionEntity;

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
    @Override
    public void TestUseCollectable() {
        // TODO Auto-generated method stub

    }
    /*
    @Test
    public void TestDuration() {
        InvincibilityPotionEntity invincibility_potion = new InvincibilityPotionEntity(0,0,0);
        CharacterEntity player = new CharacterEntity(0,0,0);
        SpiderEntity spider = new SpiderEntity();
        ArrayList<IEntity> entities = new ArrayList<>();  

        Dungeon dungeon = new Dungeon(entities, "Standard", player);
            
        invincibility_potion.used(player);
        //test player damage
        assertEquals(100, player.setDamage());
        //TO DO :test enemy moving away

            //TO DO: test battle/interaction
            //TO DO: test enemy moving away
    
            //test duration
            for (int i = 0; i < 9; i++) {
                assertEquals(invincibility_potion.getDuration(), 10 - i);
                dungeon.tick(Direction.DOWN);
                assertNotNull(player.findCollectableById(invincibility_potion.getId()), "Inventory should contain entity " + invincibility_potion.getId());
            }
            dungeon.tick(Direction.DOWN);
            assertNULL(player.findCollectableById(invincibility_potion.getId()), "Inventory should not contain entity " + invincibility_potion.getId());
        }//test duration
    }
    */
    
}