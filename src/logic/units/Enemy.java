
package logic.units;

import logic.combat.Damage;

/**
 * Represents an enemy during an Encounter.
 * 
 * @author Robert
 *
 */
public class Enemy extends Unit
{
    private String name;
    private int maxHealth;
    private int maxEnergy;
    private int curHealth;
    private int curEnergy;
    private int power;
    private int protection;
    private boolean knockedOut = false;
    private BasicAttack attack;

    public Enemy(final String charName, final int hp, final int en, final int pow,
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
}
