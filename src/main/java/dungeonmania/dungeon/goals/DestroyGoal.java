package dungeonmania.dungeon.goals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.MercenaryEntity;
import dungeonmania.entities.movingEntities.ZombieToastEntity;
import dungeonmania.entities.movingEntities.spiderEntity.SpiderEntity;

public class DestroyGoal implements IGoal {

    private String type;

    public DestroyGoal() {
        this.type = "enemies";
    }

    public boolean checkGoal(Dungeon dungeon) {
        List<IEntity> enemies = new ArrayList<>();
        enemies.addAll(dungeon.entitiesControl.getEntitiesOfType(SpiderEntity.class));
        enemies.addAll(dungeon.entitiesControl.getEntitiesOfType(MercenaryEntity.class).stream().filter(m -> !m.isBribed()).collect(Collectors.toList()));
        enemies.addAll(dungeon.entitiesControl.getEntitiesOfType(ZombieToastEntity.class));
        if (enemies.size() == 0) {
            return true;
        }
        return false;
    }
    
    public String getType() {
        return type;
    }

    @Override
    public String getFrontendString() {
        return ":mercenary";
    }
}
