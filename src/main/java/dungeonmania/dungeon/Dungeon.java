package dungeonmania.dungeon;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dungeonmania.dungeon.goals.Goals;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.collectableEntities.KeyEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.staticEntities.DoorEntity;
import dungeonmania.entities.staticEntities.PortalEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawnerEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.*;
import dungeonmania.util.Direction;
import dungeonmania.util.DungeonEntityJsonParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dungeon {
    public EntitiesControl entitiesControl;
    private GameModeType gameMode;
    private String id;
    private String dungeonName;
    public CharacterEntity player;
    private Goals goals;
    private JsonObject initalGoals;

    /**
     * Main Dungeon Constructor 
     * @param entities            list of entities in Dungeon
     * @param goalConditions      goal conditions for the dungeon
     * @param gameMode            difficulty of the dungeon
     * @param id                  identifier for the dungeon
     * @param dungeonName         name of the dungeon
     */
    public Dungeon(JsonArray entities, JsonObject goalConditions, String gameMode, String id, String dungeonName) {
        this.gameMode = GameModeType.getGameModeType(gameMode);
        this.id = id;
        this.dungeonName = dungeonName;
        this.entitiesControl = new EntitiesControl();
        this.initalGoals = goalConditions;
        for (JsonElement entityInfo : entities) {
            JsonObject entityObj = entityInfo.getAsJsonObject();
            DungeonEntityJsonParser dungeonEntityJsonInfo = new DungeonEntityJsonParser(entityObj);
            
            if (dungeonEntityJsonInfo.getType().equals(EntityTypes.PLAYER)) {
                this.player = new CharacterEntity(dungeonEntityJsonInfo.getX(), dungeonEntityJsonInfo.getY(), this.gameMode);
            } else {
                this.entitiesControl.createEntity(dungeonEntityJsonInfo, this.gameMode);
            }
        }
        entitiesControl.setPlayerStartPosition(player.getPosition());

        if (goalConditions != null) {
            this.goals = new Goals(goalConditions);
        }
    }

    /**
     * Dungeon Constructor 
     * @param entities            list of entities in Dungeon
     * @param gameMode            difficulty of the dungeon
     * @param player              player inside the dungeon
     */
    public Dungeon(ArrayList<IEntity> entities, String gameMode, CharacterEntity player) {
        this.entitiesControl = new EntitiesControl();
        this.entitiesControl.setEntities(entities);
        this.gameMode = GameModeType.getGameModeType(gameMode);
        this.player = player;
    }

    /**
     * Dungeon Constructor 
     * @param entities            list of entities in Dungeon
     * @param gameMode            difficulty of the dungeon
     * @param player              player inside the dungeon
     * @param goalConditions      goal conditions for the dungeon
     */
    public Dungeon(ArrayList<IEntity> entities, String gameMode, CharacterEntity player, JsonObject goalConditions) {
        this.entitiesControl = new EntitiesControl();
        this.entitiesControl.setEntities(entities);
        this.gameMode = GameModeType.getGameModeType(gameMode);
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

    /**
     * Dungeon tick - moves entities and sets off ticking interactions
     * @param direction            direction of the player
     */
    public void tick(Direction direction) {
        player.move(direction, entitiesControl);
        if (player.isInvincible()) {
            entitiesControl.runAwayAllMovingEntities(player);
        }
        entitiesControl.moveAllMovingEntities(player);
        entitiesControl.tick();
        entitiesControl.generateEnemyEntities(this.gameMode);
    }

    /**
     * Dungeon tick - moves entities, sets off ticking interactions and uses an item based on id
     * @param itemId            identifier of item to be used
     */
    public void tick(String itemID) {
        player.useItem(itemID, this.entitiesControl);
        if (player.isInvincible()) {
            entitiesControl.runAwayAllMovingEntities(player);
        } else {
            entitiesControl.moveAllMovingEntities(player);
        }
        entitiesControl.tick();
        entitiesControl.generateEnemyEntities(this.gameMode);
    }

    /**
     * Function for entities to interact with player
     * @param entityId identifier of entity to be interacted with
     */
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
    
     /**
     * Finds all entities of a certain type in the dungeon
     * @param type type to be found
     * @returns a list of all entities of that type in the dungeon      
     */
    public <T extends IEntity> List<T> getAllEntitiesOfType(Class<T> type) {
        return this.entitiesControl.getAllEntitiesOfType(type);
    }

    /**
     * Builds an item
     * @param buildable item to be built    
     */
    public void build(String buildable) {
        EntityTypes itemToBuild = EntityTypes.getEntityType(buildable);
        this.player.build(itemToBuild);
    }
    
    /**
     * Saves the game
     * @param saveGameName name of save file    
     */
    public void saveGame(String saveGameName) {
        Gson gson = new Gson();

        try {
            Files.createDirectories(Paths.get("savedGames"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("savedGames", saveGameName + ".json");
        JsonObject finalObject = saveCurentStateToJson();
        if (file.exists()) {
            file.delete();
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(finalObject, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(finalObject, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    /**
     * Saves current state of the game to JSON
     */
    public JsonObject saveCurentStateToJson() {
        JsonObject finalObject = new JsonObject();              
        JsonArray entities = new JsonArray();
        for (IEntity entity: entitiesControl.getEntities()) {
            int x = entity.getPosition().getX();
            int y = entity.getPosition().getY();
            String type = entity.getType().toString();
            if (entity.getType().equals(EntityTypes.KEY) ) {
                KeyEntity key = (KeyEntity) entity;
                entities.add(getJsonKeyDoor(x, y, key.getKeyNumber(), type));
            } else if (entity.getType().equals(EntityTypes.DOOR)) {
                DoorEntity door = (DoorEntity) entity;
                entities.add(getJsonKeyDoor(x, y, door.getKeyNumber(), type));
            } else if (entity.getType().equals(EntityTypes.PORTAL)) {
                PortalEntity portal = (PortalEntity) entity;
                entities.add(getJsonPortal(x, y, portal.getColour(), type));
            } else {
                entities.add(getJsonVersion(x, y, type));
            }           
        }
        for (IEntity entity: player.getInventory()) {
            entities.add(getJsonVersion(player.getPosition().getX(), player.getPosition().getY(), entity.getType().toString()));
        }
        entities.add(getJsonVersion(player.getPosition().getX(), player.getPosition().getY(), player.getType().toString()));       
        finalObject.add("entities", entities);
        finalObject.add("goal-condition", initalGoals);
        finalObject.addProperty("dungeonName", dungeonName);
        finalObject.addProperty("gameMode", gameMode.toString());
        return finalObject;
    }


    public JsonObject getJsonVersion(int x, int y, String type) {
        JsonObject entityInfo = new JsonObject();
        entityInfo.addProperty("x", x);
        entityInfo.addProperty("y", y);
        entityInfo.addProperty("type", type);
        return entityInfo;
    }

    public JsonObject getJsonKeyDoor(int x, int y, int keyNumber, String type) {
        JsonObject entityInfo = getJsonVersion(x, y, type);
        entityInfo.addProperty("key", keyNumber);
        return entityInfo;
    }

    public JsonObject getJsonPortal(int x, int y, String colour, String type) {
        JsonObject entityInfo = getJsonVersion(x, y, type);
        entityInfo.addProperty("colour", colour);
        return entityInfo;
    }
}
 