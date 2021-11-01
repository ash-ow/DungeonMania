package dungeonmania.entities.movingEntities;

import java.util.List;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.util.RandomChance;
import dungeonmania.entities.IContactingEntity;
import dungeonmania.entities.IEntity;

public interface IBattlingEntity extends IContactingEntity {
    public float getHealth();
    public void setHealth(float health);
    public float getDamage();
    public void loseHealth(float enemyHealth, float enemyDamage);

    public default void battle(EntitiesControl entitiesControl, CharacterEntity player) {
        while (
            !player.isInvisible() &&
            player.isAlive() &&
            !enemyIsAlive(entitiesControl, player)
        ) {
            doBattle(player);
        }
    }

    default boolean enemyIsAlive(EntitiesControl entitiesControl, CharacterEntity player) {
        if (!this.isAlive()) {
            dropEntities(player);
            entitiesControl.removeEntity(this);
            return true;
        }
        return false;
    }
    
    default void doBattle(CharacterEntity player) {
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
            }
            player.loseHealth(enemyInitialHealth, this.getDamage());
        }
    }

    default boolean isAlive() {
        return this.getHealth() > 0;
    }

    default void dropEntities(CharacterEntity player) {
        OneRingEntity ring = new OneRingEntity();
        if (RandomChance.getRandomBoolean(ring.getDropChance())) {
            player.addEntityToInventory(ring);
        }
    }

    default void dropEntities(CharacterEntity player, float probability) {
        OneRingEntity ring = new OneRingEntity();
        if (RandomChance.getRandomBoolean(probability)) {
            player.addEntityToInventory(ring);
        }
    }
    
    @Override
    public default void contactWithPlayer(EntitiesControl entities, CharacterEntity player) {
        battle(entities, player);
    }

}

    
