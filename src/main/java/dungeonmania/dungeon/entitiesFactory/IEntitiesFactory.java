package dungeonmania.dungeon.entitiesFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.*;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.util.Position;

public class IEntitiesFactory extends EntitiesControl {
    public void generateEntity(JsonElement entityInfo, EntitiesControl entities, GameModeType gameMode) {
        JsonObject entityObj = entityInfo.getAsJsonObject();
        EntityTypes type = EntityTypes.getEntityType(entityObj.get("type").getAsString());
        Integer xAxis = entityObj.get("x").getAsInt();
        Integer yAxis = entityObj.get("y").getAsInt();
        Integer layer = entities.getAllEntitiesFromPosition(new Position(xAxis, yAxis)).size();
        switch (type) {
            case WALL:
                this.createNewEntityOnMap(new WallEntity(xAxis, yAxis, layer));
                break;
            case EXIT:
                this.createNewEntityOnMap(new ExitEntity(xAxis, yAxis, layer));
                break;
            case SWITCH:
                this.createNewEntityOnMap(new SwitchEntity(xAxis, yAxis, layer));
                break;
            case BOULDER:
                this.createNewEntityOnMap(new BoulderEntity(xAxis, yAxis, layer));
                break;
            case SPIDER:
                this.createNewEntityOnMap(new SpiderEntity(xAxis, yAxis, layer));
                break;
            case WOOD:
                this.createNewEntityOnMap(new WoodEntity(xAxis, yAxis, layer));
                break;
            case ARROW:
                this.createNewEntityOnMap(new ArrowsEntity(xAxis, yAxis, layer));
                break;
            case BOMB:
                this.createNewEntityOnMap(new BombEntity(xAxis, yAxis, layer));
                break;
            case SWORD:
                this.createNewEntityOnMap(new SwordEntity(xAxis, yAxis, layer));
                break;
            case ARMOUR:
                this.createNewEntityOnMap(new ArmourEntity(xAxis, yAxis, layer));
                break;
            case TREASURE:
                this.createNewEntityOnMap(new TreasureEntity(xAxis, yAxis, layer));
                break;
            case HEALTH_POTION:
                this.createNewEntityOnMap(new HealthPotionEntity(xAxis, yAxis, layer));
                break;
            case INVISIBILITY_POTION:
                this.createNewEntityOnMap(new InvisibilityPotionEntity(xAxis, yAxis, layer));
                break;
            case INVINCIBILITY_POTION:
                this.createNewEntityOnMap(new InvincibilityPotionEntity(xAxis, yAxis, layer));
                break;
            case MERCENARY:
                this.createNewEntityOnMap(new MercenaryEntity(xAxis, yAxis, layer));
                break;
            case ZOMBIE_TOAST:
                this.createNewEntityOnMap(new ZombieToastEntity(xAxis, yAxis, layer));
                break;
            case ZOMBIE_TOAST_SPAWNER:
                this.createNewEntityOnMap(new ZombieToastSpawnerEntity(xAxis, yAxis, layer));
                break;
            case ONE_RING:
                this.createNewEntityOnMap(new OneRingEntity(xAxis, yAxis, layer));
        }
    }
}
