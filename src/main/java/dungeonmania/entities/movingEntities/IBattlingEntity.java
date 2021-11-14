package dungeonmania.entities.movingEntities;

import dungeonmania.dungeon.EntitiesControl;
import dungeonmania.entities.collectableEntities.ICollectable;
import dungeonmania.entities.collectableEntities.IWeaponEntity;
import dungeonmania.entities.collectableEntities.OneRingEntity;
import dungeonmania.util.RandomChance;
import dungeonmania.entities.IContactingEntity;

public interface IBattlingEntity extends IContactingEntity {
    public float getHealth();
    public void setHealth(float health);
    public float getDamage();
    public float loseHealth(float enemyHealth, float enemyDamage);

    /**
     * Checks status of the player and then calls battle function
     * @param entitiesControl  the list of all entities
     * @param player           the player which is battling
     */
    public default void battle(EntitiesControl entitiesControl, CharacterEntity player) {
        while (
            !player.isInvisible() &&
            player.isAlive() &&
            !enemyIsAlive(entitiesControl, player)
        ) {
            doBattle(player);
        }
    }

    /**
     * Checks if the enemy is alive, and if not, drops relevant entities and is removed from the list of entities
     * @param entitiesControl  the list of all entities
     * @param player           the player which is battling
     */
    default boolean enemyIsAlive(EntitiesControl entitiesControl, CharacterEntity player) {
        if (!this.isAlive()) {
            dropEntities(player);
            entitiesControl.removeEntity(this);
            return true;
        }
        return false;
    }
    
    /**
     * Battle interactions
     * @param player the player which is battling
     */
    default void doBattle(CharacterEntity player) {
        if (player.invincible){
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
                ICollectable weaponUsed = (ICollectable) weapon;
                weaponUsed.used(player);
            }
            player.loseHealth(enemyInitialHealth, this.getDamage());
        }
    }

    default boolean isAlive() {
        return this.getHealth() > 0;
    }

    /**
     * Drops relevant entities and adds the to player inventory
     * @param player           the player which is battling
     */
    default void dropEntities(CharacterEntity player) {
        OneRingEntity ring = new OneRingEntity();
        if (RandomChance.getRandomBoolean(ring.getDropChance())) {
            player.addEntityToInventory(ring);
        }
    }

    /**
     * Drops relevant entities and adds to player inventory based on probability
     * @param player           the player which is battling
     */
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

    
