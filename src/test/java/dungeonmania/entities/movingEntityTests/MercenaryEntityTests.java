package dungeonmania.entities.movingEntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IEntityTests;
import dungeonmania.entities.collectableEntities.WoodEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.WallEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MercenaryEntityTests implements IMovingEntityTest{

    @Override
    @Test
    public void TestMove() {
        EntitiesControl entitiesControl = new EntitiesControl();
        CharacterEntity player = new CharacterEntity(5, 0, 0);
        MercenaryEntity mercenary = new MercenaryEntity(0, 0, 0);
        entitiesControl.addEntities(mercenary);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(1, 0), mercenary.getPosition());
    }

    @Test
    public void TestCorrectMove() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(5, 5, 0);
        WallEntity wall = new WallEntity(4, 5, 0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(mercenary);
        entitiesControl.addEntities(wall);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(5, 4), mercenary.getPosition());
    }

    @Test
    public void TestBlocked() {
        CharacterEntity player = new CharacterEntity();
        MercenaryEntity mercenary = new MercenaryEntity(5, 5, 0);
        WallEntity wall = new WallEntity(4, 5, 0);
        WallEntity wall2 = new WallEntity(5, 4, 0);
        EntitiesControl entitiesControl = new EntitiesControl();
        entitiesControl.addEntities(mercenary);
        entitiesControl.addEntities(wall);
        entitiesControl.addEntities(wall2);
        mercenary.move(entitiesControl, player);
        assertEquals(new Position(5, 5), mercenary.getPosition());
    }
}
