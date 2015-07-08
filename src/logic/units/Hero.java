
package logic.units;

import logic.combat.Damage;

/**
 * Represents one of the player's dudes during combat.
 * 
 * @author Robert
 *
 */
public class Hero extends Unit
{
    private static final long DOG_BASIC_TIMED_HIT_TIMING = 0L;
    private static final long CATFRIEND_BASIC_TIMED_HIT_TIMING = 0L;

    private String name;
    private int maxHealth;
    private int maxEnergy;
    private int curHealth;
    private int curEnergy;
    private int power;
    private int protection;
    private boolean knockedOut = false;
    private BasicAttack attack;

    public Hero(final String charName, final int hp, final int en, final int pow,
            final int prot)
    {
        name = charName;
        maxHealth = hp;
        curHealth = hp;
        maxEnergy = en;
        curEnergy = en;
        power = pow;
        protection = prot;

        attack = new BasicAttack(pow, Damage.PHYSICAL);
    }

    public BasicAttack getBasicAttack()
    {
        return attack;
    }

    /**
     * Dog is a special type of Hero.
     * 
     * @author Robert
     *
     */
    public class Dog extends Hero
    {
        public Dog(int hp, int en, int pow, int prot)
        {
            super("Dog", hp, en, pow, prot);
        }
    }
}
