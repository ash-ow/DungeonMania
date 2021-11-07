package dungeonmania.entities.collectableEntities;

import dungeonmania.entities.movingEntities.CharacterEntity;

public interface IDefensiveEntity {
    /**
     * Reduces input damage for the player
     * @param damage input damage for the player
     * @param player player which is being damaged
     * @return redecued value for damage as a float
     */
    public float reduceDamage(float damage, CharacterEntity player);
}
