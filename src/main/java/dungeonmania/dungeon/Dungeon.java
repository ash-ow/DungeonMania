package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.IInteractingEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
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
            String type = entityObj.get("type").getAsString();
            Integer xAxis = entityObj.get("x").getAsInt();
            Integer yAxis = entityObj.get("y").getAsInt();
            Integer layer = this.entitiesControl.getAllEntitiesFromPosition(new Position(xAxis, yAxis)).size();
            
            // TODO can probably improve this with factory pattern
            // or at least by reading the JsonObject as a HashMap and passing that into a generic constructor
            // Maybe Entity class should have one more constructor which accepts a HashMap!
            if (type.equals("player")) {
                this.player = new CharacterEntity(xAxis, yAxis, layer);
            } else if (type.equals("key") || type.equals("door")) {
                Integer key = entityObj.get("key").getAsInt();
                this.entitiesControl.createEntity(xAxis, yAxis, layer, key, type);
            } else if (type.equals("portal")) {
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
        return new DungeonResponse(id, dungeonName, entitiesInfo, player.getInventoryInfo(), new ArrayList<>(), getGoals());
    }

    public void tick(Direction direction) {
        player.move(direction, entitiesControl);
        entitiesControl.moveAllMovingEntities(direction, player);
        entitiesControl.tick();
        entitiesControl.generateEnemyEntities();
    }

    public void tick(String itemType) {
        player.useItem(itemType, this.entitiesControl);
        // TODO implement
    }

    public void interact(String entityID) throws IllegalArgumentException, InvalidActionException{
        IEntity interacting = this.entitiesControl.getEntityById(entityID);
        if (interacting == null) {
            throw new IllegalArgumentException("Entity doesnt exist");
        }
        if (!(interacting instanceof IInteractingEntity)) {
            throw new IllegalArgumentException("Entity is not interactable");
        } else {
            IInteractingEntity interactor = (IInteractingEntity) interacting;
            interactor.interactWith(player);
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

    public List<IEntity> getEntities(String type) {
        return this.entitiesControl.getAllEntitiesOfType(type);
    }
}
 