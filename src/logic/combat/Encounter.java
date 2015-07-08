
package logic.combat;

import java.util.List;
import java.util.Observable;

import logic.units.Enemy;
import logic.units.Hero;
import logic.units.Hero.Dog;
import logic.units.Unit;

/**
 * Represents an encounter between a group of Heroes and a group of Enemies.
 * 
 * @author Robert
 *
 */
public class Encounter extends Observable
{
    // TODO Implement enconter logic.
    /**
     * List of fighting player controlled units. The order of actions is determined by the
     * player.
     */
    List<Hero> heros;

    /**
     * List of enemy units controlled by their AI. The order of actions is determined the
     * priority of the enemy units.
     */
    List<Enemy> enemies;

    /**
     * Starts a new encounter
     * -List of your dudes
     * -List of their dudes
     */
    public Encounter()
    {
        Hero dog = new Hero("Dog", 10, 5, 1, 1);
        Enemy squirrel = new Enemy("Squeeky Toy", 1, 0, 1, 0);

        /*
         * Your dudes have a chance to attack
         */

        /*
         * Their dudes have a chance to fight back
         */

        /*
         * Repeat until you win or lose.
         */
    }

    public void attack(final Unit aggresor, final Unit defender)
    {

    }
}
