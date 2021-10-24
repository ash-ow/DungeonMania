package dungeonmania.entities.collectableEntityTests.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.collectableEntities.ICollectableEntity;
import dungeonmania.entities.collectableEntities.Wood;
import dungeonmania.entities.collectableEntities.buildableEntities.BowEntity;

public class BowTest implements IBuildableEntityTests {
    @Test
    public void TestIsBuildable() {
        BowEntity bow = new BowEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        inventory.add(new Wood());
        inventory.add(new Wood());
        assertTrue(bow.isBuildable(inventory));
    }
    
    @Test
    public void TestIsNotBuildable() {
        BowEntity bow = new BowEntity();
        List<ICollectableEntity> inventory = new ArrayList<ICollectableEntity>();
        assertFalse(bow.isBuildable(inventory));
    }
}
