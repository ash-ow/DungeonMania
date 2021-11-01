package dungeonmania.entities.movingEntities.spiderEntity;

import dungeonmania.dungeon.EntitiesControl;

public interface SpiderState {
    /**
     * Moves the spider
     * @param movementIndex gives the spider its next direction
     * @param spider        the spider which is moving
     * @param entities      the list of entities which the spider may interact with
     * @return              Whether the spider has moved
     */
    public Boolean moveSpider(Integer movementIndex, SpiderEntity spider, EntitiesControl entities);
}
