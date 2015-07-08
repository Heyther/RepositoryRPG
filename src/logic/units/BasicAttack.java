
package logic.units;

import logic.combat.Damage;

public class BasicAttack
{
    /** Damage of the attack */
    private int attackDamage;

    /** The type of damage. Refer to the constants in the Damage class. */
    private int type;

    /** The timing of the attack for a timed hit in nanoseconds. */
    private long timing;

    public BasicAttack(final int damage)
    {
        attackDamage = damage;
        timing = 0L;
        type = 0;
    }

    /**
     * Creates a new BasicAttack with the damage value and damage type passed.
     * 
     * @param damage How much damage the attack does.
     * @param damageType What kind of damage it is. Must be between 0 and 7.
     * @throws IllegalArgumentException if the damageType is not between 0 and 7.
     */
    public BasicAttack(final int damage, final int damageType)
    {
        if (damageType < 0 || damageType > 7)
            throw new IllegalArgumentException(
                    "The damageType field must be between 0 and 7, inclusive.");

        attackDamage = damage;
        type = damageType;
        timing = 0L;
    }

    /**
     * Creates a new BasicAttack with the damage value and damage type passed.
     * 
     * @param damage How much damage the attack does.
     * @param damageType What kind of damage it is. Must be between 0 and 7.
     * @param hitTiming The timing of the attack, in nanoseconds, to execute a Timed Hit.
     * @throws IllegalArgumentException if the damageType is not between 0 and 7.
     * @throws IllegalArgumentException if the hitTiming is a number less than 0.
     */
    public BasicAttack(final int damage, final int damageType, final long hitTiming)
    {
        if (damageType < 0 || damageType > 7)
            throw new IllegalArgumentException(
                    "The damageType argument must be between 0 and 7, inclusive.");

        if (hitTiming < 0)
            throw new IllegalArgumentException(
                    "The hitTiming argument must be a positive number of type long.");

        attackDamage = damage;
        type = damageType;
        timing = hitTiming;
    }

    public Damage getDamage()
    {
        return new Damage(attackDamage, type);
    }
}
