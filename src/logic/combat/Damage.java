
package logic.combat;

public class Damage
{
    public static final int PHYSICAL = 0;
    public static final int FIRE = 1;
    public static final int WATER = 2;
    public static final int ELECTRIC = 3;
    public static final int GROUND = 4;
    public static final int LIGHT = 5;
    public static final int SHADOW = 6;

    private int damageOut;
    private int damageType;

    public Damage(final int damage, final int type)
    {
        damageOut = damage;
        damageType = type;
    }
}
