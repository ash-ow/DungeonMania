package dungeonmania.entities.collectableEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.ITicker;
import dungeonmania.entities.movingEntities.CharacterEntity;

public interface IAffectingEntity extends ITicker {
    public void activateAffects(EntitiesControl entities, CharacterEntity player);
}
