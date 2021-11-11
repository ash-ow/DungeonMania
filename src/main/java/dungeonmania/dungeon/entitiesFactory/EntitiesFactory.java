package dungeonmania.dungeon.entitiesFactory;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.spiderEntity.*;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.util.DungeonEntityJsonObject;

public class EntitiesFactory extends EntitiesControl {
    public static void generateEntity(DungeonEntityJsonObject jsonInfo, EntitiesControl entities, GameModeType gameMode) {
        switch (jsonInfo.getType()){
            case WALL: entities.createNewEntityOnMap(new WallEntity(jsonInfo)); break;
            case EXIT: entities.createNewEntityOnMap(new ExitEntity(jsonInfo)); break;
            case SWITCH: entities.createNewEntityOnMap(new SwitchEntity(jsonInfo)); break;
            case BOULDER: entities.createNewEntityOnMap(new BoulderEntity(jsonInfo)); break;
            case SPIDER: entities.createNewEntityOnMap(new SpiderEntity(jsonInfo)); break;
            case WOOD: entities.createNewEntityOnMap(new WoodEntity(jsonInfo)); break;
            case ARROW: entities.createNewEntityOnMap(new ArrowsEntity(jsonInfo)); break;
            case BOMB: entities.createNewEntityOnMap(new BombEntity(jsonInfo)); break;
            case SWORD: entities.createNewEntityOnMap(new SwordEntity(jsonInfo)); break;
            case ARMOUR: entities.createNewEntityOnMap(new ArmourEntity(jsonInfo)); break;
            case TREASURE: entities.createNewEntityOnMap(new TreasureEntity(jsonInfo)); break;
            case HEALTH_POTION: entities.createNewEntityOnMap(new HealthPotionEntity(jsonInfo)); break;
            case INVISIBILITY_POTION: entities.createNewEntityOnMap(new InvisibilityPotionEntity(jsonInfo)); break;
            case INVINCIBILITY_POTION: entities.createNewEntityOnMap(new InvincibilityPotionEntity(jsonInfo)); break;
            case MERCENARY: entities.createNewEntityOnMap(new MercenaryEntity(jsonInfo)); break;
            case ZOMBIE_TOAST: entities.createNewEntityOnMap(new ZombieToastEntity(jsonInfo)); break;
            case ZOMBIE_TOAST_SPAWNER: entities.createNewEntityOnMap(new ZombieToastSpawnerEntity(jsonInfo)); break;
            case ONE_RING: entities.createNewEntityOnMap(new OneRingEntity(jsonInfo)); break;
            case KEY: entities.createNewEntityOnMap(new KeyEntity(jsonInfo)); break;
            case PORTAL: entities.createNewEntityOnMap(new PortalEntity(jsonInfo)); break;
            case DOOR: entities.createNewEntityOnMap(new DoorEntity(jsonInfo)); break;
            case TIME_TRAVEL_PORTAL: entities.createNewEntityOnMap(new TimeTravelPortalEntity(jsonInfo)); break;
            case TIME_TURNER: entities.createNewEntityOnMap(new TimeTurnerEntity(jsonInfo)); break;
            case SWAMP:entities.createNewEntityOnMap(new SwampEntity(jsonInfo)); break;
            case SCEPTRE:entities.createNewEntityOnMap(new SceptreEntity(jsonInfo)); break;
            case SUN_STONE:entities.createNewEntityOnMap(new SunStoneEntity(jsonInfo)); break;
            case ASSASSIN: entities.createNewEntityOnMap(new AssassinEntity(jsonInfo));
        }
    }
}
