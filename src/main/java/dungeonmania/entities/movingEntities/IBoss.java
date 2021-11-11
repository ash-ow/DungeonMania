package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.AndurilEntity;
import dungeonmania.entities.collectableEntities.CollectableEntity;
import dungeonmania.entities.collectableEntities.IWeaponEntity;

public interface IBoss extends IBattlingEntity {
    public void deactivteSpecialAbility();
    
    @Override
    default void doBattle(CharacterEntity player) {
        if (player.getItemsFromInventory(AndurilEntity.class).size() > 0) {
            this.deactivteSpecialAbility();
        }
        if (player.isInvincible()){
            this.setHealth(0);
        }
        else {
            float enemyInitialHealth = this.getHealth();
            this.loseHealth(player.getHealth(), player.getDamage());
            for (IBattlingEntity teammate : player.teammates) {
                this.loseHealth(teammate.getHealth(), teammate.getDamage());
            }
            for (IWeaponEntity weapon : EntitiesControl.getEntitiesOfType(player.getInventory(), IWeaponEntity.class)) {
                weapon.attack(this, player);
                CollectableEntity weaponUsed = (CollectableEntity) weapon;
                weaponUsed.used(player);
            }
            player.loseHealth(enemyInitialHealth, this.getDamage());
        }
    }

}
