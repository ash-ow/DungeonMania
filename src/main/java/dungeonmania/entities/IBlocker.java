package dungeonmania.entities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.movingEntities.IMovingEntity;
import dungeonmania.util.Direction;

public interface IBlocker {
    /**
     * @return true if the blocker is still preventing the IMovingEntity from entering its position
     */
    public boolean isBlocking();

    /**
     * Will try to unblock the IBlocker if needed
     * @param ent
     * @param direction
     */
    default boolean tryUnblock(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl) {
        if (this.isBlocking()) {
            return this.unblockCore(ent, direction, entitiesControl);
        }
        return true;
    }

    /**
     * Core function for unblocking this IBlocker with ent.
     * @param ent
     * @return true if successfully unblocked
     */
    public boolean unblockCore(IMovingEntity ent, Direction direction, EntitiesControl entitiesControl);
}
