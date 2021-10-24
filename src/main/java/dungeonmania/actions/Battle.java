package dungeonmania.actions;

import dungeonmania.entities.IEntity;
import dungeonmania.entities.movingEntities.CharacterEntity;

public class Battle implements IAction {
    public Battle(CharacterEntity character, IEntity enemy) {
        System.out.println("The character is battling " + enemy.getId());
    }
}
