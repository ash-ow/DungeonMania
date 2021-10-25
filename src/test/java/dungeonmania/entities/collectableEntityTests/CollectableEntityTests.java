package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.Wood;

public class CollectableEntityTests {
    @Test
    public void testWood() {
        Wood wood = new Wood("wood 1", 1, 0, "wood");
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(wood);
    }
    
}
