package dungeonmania.entities.buildableEntityTest;

import dungeonmania.entities.IEntityTests;

public interface IBuildableEntityTests extends IEntityTests {
    public void TestIsBuildable();
    public void TestIsNotBuildable_EmptyInventory();
    public void TestIsNotBuildable_InventoryFullOfWrongItems();
    public void TestIsNotBuildable_InsufficientCorrectItems();
}
