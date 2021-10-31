package dungeonmania.entities.buildableEntityTest;

import static org.junit.jupiter.api.Assertions.assertNull;

import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.buildableEntities.BuildableEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public interface IBuildableEntityTests extends IEntityTests {
    public void TestIsBuildable();
    public void TestIsNotBuildable_EmptyInventory();
    public void TestIsNotBuildable_InventoryFullOfWrongItems();
    public void TestIsNotBuildable_InsufficientCorrectItems();
}
