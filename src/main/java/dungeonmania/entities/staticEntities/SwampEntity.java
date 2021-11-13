package dungeonmania.entities.staticEntities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityTypes;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.ITicker;


public class SwampEntity extends Entity implements ITicker {
    public SwampEntity() {
        this(0, 0);
    }
    
    public SwampEntity(int x, int y) {
        super(x, y, EntityTypes.SWAMP);
    }

    public SwampEntity(JsonObject jsonInfo) {
        this(jsonInfo.get("x").getAsInt(), jsonInfo.get("y").getAsInt());
    }

    /** The maximum number of ticks a non-player entity must remain in the swamp */
    int ticksRequiredToPass = 3;
    /** 
     * @key the entity
     * @value the number of ticks they must still remain in the swamp
     */
    Map<IEntity, Integer> entitiesTryingToPass = new HashMap<IEntity, Integer>();

    @Override
    public void tick(EntitiesControl entitiesControl) {
        List<IEntity> stuckEntities = entitiesControl.getAllEntitiesFromPosition(this.position);
        addNewEntitiesToEntitiesTryingToPass(stuckEntities);
        keepEntityStuckIfNeeded();
    }

    void addNewEntitiesToEntitiesTryingToPass(List<IEntity> stuckEntities) {
        for (IEntity entity : stuckEntities) {
            if (!this.entitiesTryingToPass.containsKey(entity) && entity != this) {
                this.entitiesTryingToPass.put(entity, this.ticksRequiredToPass);
            }
        }
    }

    void keepEntityStuckIfNeeded() {
        for (Map.Entry<IEntity, Integer> entry : this.entitiesTryingToPass.entrySet()) {
            IEntity entity = entry.getKey();
            int ticksRemaining = entry.getValue();
            if (ticksRemaining > 0) {
                entity.setPosition(this.position);
                this.entitiesTryingToPass.put(entity, ticksRemaining - 1);
            } else {
                this.entitiesTryingToPass.remove(entity);
            }
        }
    }
}
