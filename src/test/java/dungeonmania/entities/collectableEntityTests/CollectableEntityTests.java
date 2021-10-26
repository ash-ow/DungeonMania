package dungeonmania.entities.collectableEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.util.Position;
import dungeonmania.entities.collectableEntities.ArrowsEntity;
import dungeonmania.entities.collectableEntities.SwordEntity;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.collectableEntities.BombEntity;
import dungeonmania.entities.collectableEntities.ArmourEntity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class CollectableEntityTests implements IEntityTests {
    @Override
    @Test
    public void TestEntityResponseInfo() {
        WoodEntity wood = new WoodEntity(0, 0, 0);
        assertEntityResponseInfoEquals(wood, "wood-0-0-0", "wood", new Position(0,0), true);

        ArrowsEntity arrows = new ArrowsEntity(0,1,0);
        assertEntityResponseInfoEquals(arrows, "arrows-0-1-0", "arrows", new Position(0,1), true);

        BombEntity bomb = new BombEntity(0,2,0);
        assertEntityResponseInfoEquals(bomb, "bomb-0-2-0", "bomb", new Position(0,2), true);

        SwordEntity sword = new SwordEntity(0,3,0);
        assertEntityResponseInfoEquals(sword, "sword-0-3-0", "sword", new Position(0,3), true);

        ArmourEntity armour = new ArmourEntity(0,4,0);
        assertEntityResponseInfoEquals(armour, "armour-0-4-0", "bomb", new Position(0,4), true);
    }
    
    // @Test
    // public void TestWoodCollect() {
    //     CharacterEntity player = new CharacterEntity(0, 0, 0);
    //     WoodEntity wood = new WoodEntity(0,0,0);
    //     assertEntityResponseInfoEquals(wood, "wood-0-0-0", "wood", new Position(0,0), true);
    //     ArrayList<IEntity> entities = new ArrayList<>();
        
    //     entities.add(wood);
    //     Dungeon dungeon = new Dungeon(20, 20, entities, "Standard", player);
    //     wood.collected();

    //     //Player.inventory
    //     //assertEquals();


    // }


}


