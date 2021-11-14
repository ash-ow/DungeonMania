package dungeonmania.generators;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.dungeon.GameModeType;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.movingEntities.BoulderEntity;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;
import dungeonmania.entities.staticEntities.ZombieToastSpawnerEntity;
import dungeonmania.util.Position;
import dungeonmania.util.RandomChance;

public class EnemyEntityGenerator {
    private static Random rand = new Random();
    private static Integer tickCounter;
    private static GameModeType gameMode;
    private static Position playerStartPosition;
    public final static HashMap<GameModeType, Double> difficulty;
    static {
        difficulty = new HashMap<>();
        difficulty.put(GameModeType.HARD, 20.0/15.0);
        difficulty.put(GameModeType.PEACEFUL, 15.0/20.0);
        difficulty.put(GameModeType.STANDARD, 1.0);
    }

    public static void generateEnemyEntities(EntitiesControl entities, Integer counter, 
            GameModeType mode, Position startPosition) {
        tickCounter = counter;
        playerStartPosition = startPosition;
        gameMode = mode;
        generateSpider(entities);
        generateFromZombieToastSpawner(entities);
        generateMercenary(entities);
    }

    /**
     * Generates mercenaries based on game mode
     * @param gameMode         difficulty of the game
     */
    private static void generateMercenary(EntitiesControl entities) {
        if (tickCounter % (int) Math.ceil(30 / difficulty.get(gameMode)) == 0) {
            List<IMovingEntity> enemy = entities.getEntitiesOfType(IMovingEntity.class);
            if (enemy.size() > 0) {
                if (RandomChance.getRandomBoolean(.3f)) {
                    entities.createEntity(playerStartPosition.getX(), playerStartPosition.getY(), EntityTypes.ASSASSIN);
                } else {
                    entities.createEntity(playerStartPosition.getX(), playerStartPosition.getY(), EntityTypes.MERCENARY);
                }
            }
        }
    }

    /**
     * Generates spider based on game mode
     * @param gameMode         difficulty of the game
     */
    private static void generateSpider(EntitiesControl entities) {
        List<SpiderEntity> spiders = entities.getAllEntitiesOfType(SpiderEntity.class);
        if (spiders.size() < 4) {
            Position largestCoordinate = entities.getLargestCoordinate();
            int largestX = largestCoordinate.getX();
            int largestY = largestCoordinate.getY();
            int randomX = rand.nextInt(largestX);
            int randomY = rand.nextInt(largestY);
            if (RandomChance.getRandomBoolean((float) (.05f * difficulty.get(gameMode)))
                && !entities.positionContainsEntityType(new Position(randomX, randomY), BoulderEntity.class)) {
                    entities.createEntity(randomX, randomY, EntityTypes.SPIDER);
            }
        }
    }
    

    /**
     * Generates zombie toast based on game mode
     * @param gameMode         difficulty of the game
     */
    private static void generateFromZombieToastSpawner(EntitiesControl entities) {
        List<ZombieToastSpawnerEntity> spawnerEntities = entities.getEntitiesOfType(ZombieToastSpawnerEntity.class);
        if (tickCounter % (int) Math.ceil(20 / difficulty.get(gameMode)) == 0) {
            for (ZombieToastSpawnerEntity spawner : spawnerEntities) {
                entities.createEntity(
                    spawner.getPosition().getX(), 
                    spawner.getPosition().getY(), 
                    EntityTypes.ZOMBIE_TOAST
                );
            }
        } else if (tickCounter % 50 == 0 && gameMode.equals(GameModeType.HARD)) {
            for (ZombieToastSpawnerEntity spawner : spawnerEntities) {
                entities.createEntity(
                    spawner.getPosition().getX(), 
                    spawner.getPosition().getY(), 
                    EntityTypes.HYDRA
                );
            }
        }
    }
    
}
