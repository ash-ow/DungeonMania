package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.InvisibilityPotionEntity;

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
    public void TestUseCollectable() {
        // TODO Auto-generated method stub
        
    }
}