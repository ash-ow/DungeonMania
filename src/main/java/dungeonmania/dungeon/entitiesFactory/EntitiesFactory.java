package dungeonmania.dungeon.entitiesFactory;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.util.JsonControl;

public class EntitiesFactory extends EntitiesControl {
    public static void generateEntity(JsonControl JsonInfo, EntitiesControl entities, GameModeType gameMode) {
        switch (JsonInfo.getType()){
            case WALL:
                entities.createNewEntityOnMap(new WallEntity(JsonInfo));
                break;
            case EXIT:
                entities.createNewEntityOnMap(new ExitEntity(JsonInfo));
                break;
            case SWITCH:
                entities.createNewEntityOnMap(new SwitchEntity(JsonInfo));
                break;
            case BOULDER:
                entities.createNewEntityOnMap(new BoulderEntity(JsonInfo));
                break;
            case SPIDER:
                entities.createNewEntityOnMap(new SpiderEntity(JsonInfo));
                break;
            case WOOD:
                entities.createNewEntityOnMap(new WoodEntity(JsonInfo));
                break;
            case ARROW:
                entities.createNewEntityOnMap(new ArrowsEntity(JsonInfo));
                break;
            case BOMB:
                entities.createNewEntityOnMap(new BombEntity(JsonInfo));
                break;
            case SWORD:
                entities.createNewEntityOnMap(new SwordEntity(JsonInfo));
                break;
            case ARMOUR:
                entities.createNewEntityOnMap(new ArmourEntity(JsonInfo));
                break;
            case TREASURE:
                entities.createNewEntityOnMap(new TreasureEntity(JsonInfo));
                break;
            case HEALTH_POTION:
                entities.createNewEntityOnMap(new HealthPotionEntity(JsonInfo));
                break;
            case INVISIBILITY_POTION:
                entities.createNewEntityOnMap(new InvisibilityPotionEntity(JsonInfo));
                break;
            case INVINCIBILITY_POTION:
                entities.createNewEntityOnMap(new InvincibilityPotionEntity(JsonInfo));
                break;
            case MERCENARY:
                entities.createNewEntityOnMap(new MercenaryEntity(JsonInfo));
                break;
            case ZOMBIE_TOAST:
                entities.createNewEntityOnMap(new ZombieToastEntity(JsonInfo));
                break;
            case ZOMBIE_TOAST_SPAWNER:
                entities.createNewEntityOnMap(new ZombieToastSpawnerEntity(JsonInfo));
                break;
            case ONE_RING:
                entities.createNewEntityOnMap(new OneRingEntity(JsonInfo));
        }
    }
}
