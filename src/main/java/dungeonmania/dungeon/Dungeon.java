package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawnerEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Dungeon {
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
                case ("mercenary"):
                    MercenaryEntity mercenaryEntity = (MercenaryEntity) interacting;
                    mercenaryEntity.interactWith(player);
                    break;
                case ("zombie_toast_spawner"):
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

    public List<IEntity> getEntities(String type) {
        return this.entitiesControl.getAllEntitiesOfType(type);
    }

    public void saveGame(String saveGameName, JsonObject goals) {
        Gson gson = new Gson();
        File file = new File("src/main/java/dungeonmania/savedGames/", saveGameName + ".json");
        JsonObject finalObject = new JsonObject();
        JsonArray entities = new JsonArray();
        for (IEntity entity: entitiesControl.getEntities()) {
            JsonObject entityInfo = new JsonObject();
            entityInfo.addProperty("x", entity.getPosition().getX());
            entityInfo.addProperty("y", entity.getPosition().getY());
        }
        finalObject.add("entities", new JsonArray());
        if (file.exists()) {
            try (FileWriter writer = new FileWriter(file, true)) {
                gson.toJson(goals, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(goals, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }



    public static void main(String[] args) {
        CharacterEntity player = new CharacterEntity(0, 1, 0);
        BoulderEntity boulder = new BoulderEntity(0, 2, 0);
        ArrayList<IEntity> entities = new ArrayList<>();
        entities.add(boulder);
        String jsonGoals = "{ \"goal\": \"boulders\"}";
        JsonObject j = new Gson().fromJson(jsonGoals, JsonObject.class);
        Dungeon dungeon = new Dungeon(entities, "Standard", player, j);
        dungeon.saveGame("player1", j);
    }
}
 