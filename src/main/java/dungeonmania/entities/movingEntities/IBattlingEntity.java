package dungeonmania.entities.movingEntities;

import dungeonmania.entities.IEntity;

public interface IBattlingEntity extends IEntity {
    public float getHealth();
    public void setHealth(float health);
    public int getDamage();
    public void setDamage(int damage);
    
    public void loseHealth(float enemyHealth, int enemyDamage);

    public default void Battle(CharacterEntity character) {
        if (this instanceof CharacterEntity) {
            System.out.println("The inner turmoil is too much");
        }
        else {
            doBattle(character);
            checkCharacterDeath(character);
            checkEnemyDeath();
        }
    }

    default void checkCharacterDeath(CharacterEntity character) {
        if (character.getHealth() <= 0) {
            // TODO make game over functionality
            System.out.println("you died");
        }
    }

    default void checkEnemyDeath() {
        if (this.getHealth() <= 0) {
            // TODO remove enemy from the game
            System.out.println("gg ez" + this.getId());
        }
    }
    
    default void doBattle(CharacterEntity character) {
        System.out.println("The character is battling " + this.getId());
        float enemyInitialHealth = this.getHealth();
        this.loseHealth(character.getHealth(), character.getDamage());
        character.loseHealth(enemyInitialHealth, this.getDamage());
    }
}
