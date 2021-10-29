package dungeonmania.entities;

import dungeonmania.dungeon.EntitiesControl;

/**
 * Entities which implement the ITicker interface should do some passive action after the moving entities move
 */
public interface ITicker {
    public void tick(EntitiesControl entitiesControl);
}
