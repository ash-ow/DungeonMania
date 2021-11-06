package dungeonmania.dungeon.entitiesFactory;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.Entity;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.util.DungeonEntityJsonParser;

public class EntitiesFactory extends EntitiesControl {
    public static void generateEntity(DungeonEntityJsonParser jsonInfo, EntitiesControl entities, GameModeType gameMode) {
        IEntity entity;
        switch (jsonInfo.getType()){
            case WALL: entity = new WallEntity(jsonInfo); break;
            case EXIT: entity = new ExitEntity(jsonInfo); break;
            case SWITCH: entity = new SwitchEntity(jsonInfo); break;
            case BOULDER: entity = new BoulderEntity(jsonInfo); break;
            case SPIDER: entity = new SpiderEntity(jsonInfo); break;
            case WOOD: entity = new WoodEntity(jsonInfo); break;
            case ARROW: entity = new ArrowsEntity(jsonInfo); break;
            case BOMB: entity = new BombEntity(jsonInfo); break;
            case SWORD: entity = new SwordEntity(jsonInfo); break;
            case ARMOUR: entity = new ArmourEntity(jsonInfo); break;
            case TREASURE: entity = new TreasureEntity(jsonInfo); break;
            case HEALTH_POTION: entity = new HealthPotionEntity(jsonInfo); break;
            case INVISIBILITY_POTION: entity = new InvisibilityPotionEntity(jsonInfo); break;
            case INVINCIBILITY_POTION: entity = new InvincibilityPotionEntity(jsonInfo); break;
            case MERCENARY: entity = new MercenaryEntity(jsonInfo); break;
            case ZOMBIE_TOAST: entity = new ZombieToastEntity(jsonInfo); break;
            case ZOMBIE_TOAST_SPAWNER: entity = new ZombieToastSpawnerEntity(jsonInfo); break;
            case ONE_RING: entity = new OneRingEntity(jsonInfo); break;
            case KEY: entity = new KeyEntity(jsonInfo); break;
            case PORTAL: entity = new PortalEntity(jsonInfo); break;
            case DOOR: entity = new DoorEntity(jsonInfo);
            default: entity = new WallEntity(jsonInfo);
        }
        entities.createNewEntityOnMap(entity);
    }
}
