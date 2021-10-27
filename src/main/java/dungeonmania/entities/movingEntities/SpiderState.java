package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;

public interface SpiderState {

    public Boolean moveSpider(Integer movementIndex, SpiderEntity spider, EntitiesControl entities);
}
