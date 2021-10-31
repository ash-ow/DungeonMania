package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawnerEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private int height;
    private int width;
    public EntitiesControl entitiesControl;
    private String gameMode;
    private String id;
    private String dungeonName;
    public CharacterEntity player;
    private Goals goals;

    /**
     * Main Dungeon Constructor if goalConditions exist
     * @param type
     * @return
     */
    public Dungeon(JsonArray entities, JsonObject goalConditions, String gameMode, String id, String dungeonName) {
        this.gameMode = gameMode;
        this.id = id;
        this.dungeonName = dungeonName;
        this.entitiesControl = new EntitiesControl();
        for (JsonElement entityInfo : entities) {
            JsonObject entityObj = entityInfo.getAsJsonObject();
            EntityTypes type = EntityTypes.getEntityType(entityObj.get("type").getAsString());
            Integer xAxis = entityObj.get("x").getAsInt();
            Integer yAxis = entityObj.get("y").getAsInt();
            Integer layer = this.entitiesControl.getAllEntitiesFromPosition(new Position(xAxis, yAxis)).size();
            
            // TODO can probably improve this with factory pattern
            // or at least by reading the JsonObject as a HashMap and passing that into a generic constructor
            // Maybe Entity class should have one more constructor which accepts a HashMap!
            if (type.equals(EntityTypes.PLAYER)) {
                this.player = new CharacterEntity(xAxis, yAxis, layer);
            } else if (type.equals(EntityTypes.KEY) || type.equals(EntityTypes.DOOR)) {
                Integer key = entityObj.get("key").getAsInt();
                this.entitiesControl.createEntity(xAxis, yAxis, layer, key, type);
            } else if (type.equals(EntityTypes.PORTAL)) {
                String colour = entityObj.get("colour").getAsString();
                this.entitiesControl.createEntity(xAxis, yAxis, layer, colour, type);
            } else {
                this.entitiesControl.createEntity(entityObj);
            }            
        }
        if (goalConditions != null) {
            this.goals = new Goals(goalConditions);
        }
    }

    public Dungeon(ArrayList<IEntity> entities, String gameMode, CharacterEntity player) {
        this.entitiesControl = new EntitiesControl();
        this.entitiesControl.setEntities(entities);
        this.gameMode = gameMode;
        this.player = player;
    }

    public Dungeon(ArrayList<IEntity> entities, String gameMode, CharacterEntity player, JsonObject goalConditions) {
        this.entitiesControl = new EntitiesControl();
        this.entitiesControl.setEntities(entities);
        this.gameMode = gameMode;
        this.player = player;
        this.goals = new Goals(goalConditions);
    }

    public DungeonResponse getInfo() {
        List<EntityResponse> entitiesInfo = new ArrayList<>();
        for (IEntity entity : entitiesControl.getEntities()) {
            entitiesInfo.add(entity.getInfo());          
        }
        if (player.isAlive()) {
            entitiesInfo.add(player.getInfo());
        }        
        return new DungeonResponse(id, dungeonName, entitiesInfo, player.getInventoryInfo(), player.getBuildableList(), getGoals());
    }

    public void tick(Direction direction) {
        player.move(direction, entitiesControl);
        if (player.getInvincible()) {
            entitiesControl.runAwayAllMovingEntities(player);
        } else {
            entitiesControl.moveAllMovingEntities(player);
        }
        entitiesControl.tick();
        entitiesControl.generateEnemyEntities();
    }

    public void tick(String itemID) {
        player.useItem(itemID, this.entitiesControl);
        if (player.getInvincible()) {
            entitiesControl.runAwayAllMovingEntities(player);
        } else {
            entitiesControl.moveAllMovingEntities(player);
        }
        entitiesControl.tick();
        entitiesControl.generateEnemyEntities();
    }

    public void interact(String entityID) throws IllegalArgumentException, InvalidActionException{
        IEntity interacting = this.entitiesControl.getEntityById(entityID);
        if (interacting == null) {
            throw new IllegalArgumentException("Entity doesnt exist");
        } else {
            switch (interacting.getType()) {
                case MERCENARY:
                    MercenaryEntity mercenaryEntity = (MercenaryEntity) interacting;
                    mercenaryEntity.interactWith(player);
                    break;
                case ZOMBIE_TOAST_SPAWNER:
                    ZombieToastSpawnerEntity spawner = (ZombieToastSpawnerEntity) interacting;
                    spawner.interactWith(entitiesControl, player);
                    break;
                default:
                    throw new IllegalArgumentException("Entity is not interactable");
            }
        }
    }

    public String getGoals() {
        if (goals == null) {
            return "";
        }
        return goals.checkGoals(this);
    }

    public CharacterEntity getPlayer() {
        return this.player;
    }

    public <T extends IEntity> List<T> getAllEntitiesOfType(Class<T> type) {
        return this.entitiesControl.getAllEntitiesOfType(type);
    }

    public void build(String buildable) {
        EntityTypes itemToBuild = EntityTypes.getEntityType(buildable);
        this.player.build(itemToBuild);
    }
}
 