package dungeonmania.dungeon.entitiesFactory;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.collectableEntities.buildableEntities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.spiderEntity.*;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.exceptions.InvalidActionException;

public class EntitiesFactory extends EntitiesControl {
    /**
     *
     */
    private static final String CANNOT_ADD_ENTITY_TO_MAP = "Cannot add entity to map: ";

    public static void generateEntity(JsonObject jsonInfo, EntitiesControl entities) {
        IEntity entity = null;
        EntityTypes type = EntityTypes.getEntityType(jsonInfo.get("type").getAsString());
        switch (type) {
            // Map entities
            case WALL:                   entity = new WallEntity(jsonInfo);                  break;
            case EXIT:                   entity = new ExitEntity(jsonInfo);                  break;
            case SWITCH:                 entity = new SwitchEntity(jsonInfo);                break;
            case BOULDER:                entity = new BoulderEntity(jsonInfo);               break;
            case SPIDER:                 entity = new SpiderEntity(jsonInfo);                break;
            case WOOD:                   entity = new WoodEntity(jsonInfo);                  break;
            case ARROW:                  entity = new ArrowsEntity(jsonInfo);                break;
            case BOMB:                   entity = new BombEntity(jsonInfo);                  break;
            case SWORD:                  entity = new SwordEntity(jsonInfo);                 break;
            case ARMOUR:                 entity = new ArmourEntity(jsonInfo);                break;
            case TREASURE:               entity = new TreasureEntity(jsonInfo);              break;
            case HEALTH_POTION:          entity = new HealthPotionEntity(jsonInfo);          break;
            case INVISIBILITY_POTION:    entity = new InvisibilityPotionEntity(jsonInfo);    break;
            case INVINCIBILITY_POTION:   entity = new InvincibilityPotionEntity(jsonInfo);   break;
            case MERCENARY:              entity = new MercenaryEntity(jsonInfo);             break;
            case ZOMBIE_TOAST:           entity = new ZombieToastEntity(jsonInfo);           break;
            case ZOMBIE_TOAST_SPAWNER:   entity = new ZombieToastSpawnerEntity(jsonInfo);    break;
            case ONE_RING:               entity = new OneRingEntity(jsonInfo);               break;
            case KEY:                    entity = new KeyEntity(jsonInfo);                   break;
            case PORTAL:                 entity = new PortalEntity(jsonInfo);                break;
            case DOOR:                   entity = new DoorEntity(jsonInfo);                  break;
            case TIME_TRAVEL_PORTAL:     entity = new TimeTravelPortalEntity(jsonInfo);      break;
            case TIME_TURNER:            entity = new TimeTurnerEntity(jsonInfo);            break;
            case SWAMP:                  entity = new SwampEntity(jsonInfo);                 break;
            case SCEPTRE:                entity = new SceptreEntity(jsonInfo);               break;
            case SUN_STONE:              entity = new SunStoneEntity(jsonInfo);              break;
            case ASSASSIN:               entity = new AssassinEntity(jsonInfo);              break;
            case HYDRA:                  entity = new HydraEntity(jsonInfo);                 break;   
            case LIGHT_BULB_OFF:         entity = new LightBulbEntity(jsonInfo);             break;
            case WIRE:                   entity = new WireEntity(jsonInfo);                  break;
            case ANDURIL:                entity = new AndurilEntity(jsonInfo);               break;

            // Cannot create an instance of these items directly on the map
            case BOW:             throw new InvalidActionException(CANNOT_ADD_ENTITY_TO_MAP + EntityTypes.BOW.toString());
            case LIGHT_BULB_ON:   throw new InvalidActionException(CANNOT_ADD_ENTITY_TO_MAP + EntityTypes.LIGHT_BULB_ON.toString());
            case MIDNIGHT_ARMOUR: throw new InvalidActionException(CANNOT_ADD_ENTITY_TO_MAP + EntityTypes.MIDNIGHT_ARMOUR.toString());
            case OLDER_PLAYER:    throw new InvalidActionException(CANNOT_ADD_ENTITY_TO_MAP + EntityTypes.OLDER_PLAYER.toString());
            case PLAYER:          throw new InvalidActionException(CANNOT_ADD_ENTITY_TO_MAP + EntityTypes.PLAYER.toString());
            case SHIELD:          throw new InvalidActionException(CANNOT_ADD_ENTITY_TO_MAP + EntityTypes.SHIELD.toString());
            case UNLOCKED_DOOR:   throw new InvalidActionException(CANNOT_ADD_ENTITY_TO_MAP + EntityTypes.UNLOCKED_DOOR.toString());
        }

        entities.createNewEntityOnMap(entity);
    }
}
