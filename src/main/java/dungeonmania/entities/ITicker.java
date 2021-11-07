package dungeonmania.entities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.staticEntities.SwitchEntity;

/**
 * Entities which implement the ITicker interface should do some passive action after the moving entities move
 */
public interface ITicker {
    public void tick(EntitiesControl entitiesControl);

    /**
     * Checks if adjacent switches are active
     * @param adjacentEntities list of adjacent entities 
     * @return                 true if there are adjacent switches which are active and false otherwise
     */
    public default boolean containsActiveSwitch(List<IEntity> entities) {
        return EntitiesControl
        .getEntitiesOfType(entities, SwitchEntity.class)
        .stream()
        .anyMatch(SwitchEntity::isActive);
    }
}
