package dungeonmania.entities.collectableEntityTests;

import dungeonmania.entities.IEntityTests;

public interface ICollectableEntityTest extends IEntityTests {
    public void TestUseCollectable();

    // TODO collect functionality is very similar for all ICollectableEntities 
    // can we add a default / helper method like in IMovingEntities?
    public void TestCollect();
}
