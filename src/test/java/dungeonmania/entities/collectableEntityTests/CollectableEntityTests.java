package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.IEntityTests;

public class CollectableEntityTests implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        WoodEntity wood = new WoodEntity(0, 0, 0);
        assertEntityResponseInfoEquals(wood, "wood-0-0-0", "wood", new Position(0,0), false);

        ArrowsEntity arrows = new ArrowsEntity(0,1,0);
        assertEntityResponseInfoEquals(arrows, "arrows-0-1-0", "arrows", new Position(0,1), false);

        BombEntity bomb = new BombEntity(0,2,0);
        assertEntityResponseInfoEquals(bomb, "bomb-0-2-0", "bomb", new Position(0,2), false);

        SwordEntity sword = new SwordEntity(0,3,0);
        assertEntityResponseInfoEquals(sword, "sword-0-3-0", "sword", new Position(0,3), false);

        ArmourEntity armour = new ArmourEntity(0,4,0);
        assertEntityResponseInfoEquals(armour, "armour-0-4-0", "armour", new Position(0,4), false);
    }
}


