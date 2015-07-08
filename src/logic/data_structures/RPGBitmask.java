
package logic.data_structures;

import java.io.Serializable;

/**
 * An immutable class which allows 32 boolean values to be stored in the space of a single
 * integer, 8 bytes.<br>
 * When specifying a bit for any of the methods, keep in mind that the first bit is bit 0 and
 * the last bit is bit 31. Numbers outside this range will throw exceptions.<br>
 * 
 * @version <b>1.0</b> <u>June 29th 2015</u><br>
 *          Created class and wrote initial methods.<br>
 *          <b>1.1</b> <u>June 30th 2015</u><br>
 *          Added class constants for a new RPGBitmask object, which are intended to
 *          be used instead of the constructor.<br>
 *          Deprecated the public, no argument constructor.<br>
 *          Added method RPGBitMask toggleBit(int). More information can be found in
 *          it's javadoc. <br>
 *          Updated documentation of most methods.<br>
 *          Implemented equals(Object), and hashcode() and added documentation.<br>
 *          <b>1.1b</b> <u>July 1st 2015</u><br>
 *          Renamed class to RPGBitmask. All methods updated accordingly.<br>
 *          Corrected grammar and spelling errors in some documentation.<br>
 *          <b>1.2</b> <u>July 2nd 2015</u><br>
 *          Rewrote toggleBit(int) to no longer use other methods within the
 *          class.<br>
 *          Rewrote equals(Object) to be less redundant.
 *          Implemented Serializable.<br>
 * 
 * @author Robert Ferguson: Primary Coder.
 */
public final class RPGBitmask implements Serializable
{
    /** Serial Version UID */
    private static final long serialVersionUID = 1603455540404993811L;

    /** The bits storing the booleans. */
    private final int bits;

    /** An RPGBitmask with all the booleans set to false. */
    public final static RPGBitmask ALL_FALSE = new RPGBitmask(0);

    /** An RPGBitmask with all the booleans set to true. */
    public final static RPGBitmask ALL_TRUE = new RPGBitmask(~0);

    /**
     * Creates a new RPGBitMask with the bits all initially set to 0.
     * 
     * @Deprecated Use the RPGBitMask.ALL_FALSE or RPGBitMask.ALL_TRUE class constants instead.
     */
    public RPGBitmask()
    {
        this(0);
    }

    /**
     * Creates a new RPGBitMask with the bits being equal to the bits from a passed integer.
     * 
     * @param theBits the new booleans.
     */
    private RPGBitmask(final int theBits)
    {
        bits = theBits;
    }

    /**
     * Returns a new RPGBitMask object with the specified bit turned off. Has no effect if
     * the bit is already off.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31, inclusive.
     * @throws IllegalArgumentException if the passed number is out of range.
     * @return A new RPGBitMask object.
     */
    public RPGBitmask turnBitOff(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return new RPGBitmask(bits & ~(1 << toggle));
    }

    /**
     * Returns a new RPGBitMask object with the specified bit turned on. Has no effect
     * if the bit is already on.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31, inclusive.
     * @throws IllegalArgumentException if the passed number is out of range.
     * @return A new RPGBitMask object.
     */
    public RPGBitmask turnBitOn(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return new RPGBitmask(bits | (1 << toggle));
    }

    /**
     * Returns a new RPGBitMask object with the specified bit toggled.<br>
     * That is, if the bit is currently on, it is turned off and if the bit is currently off,
     * it is turned on.<br>
     * <br>
     * Note that unlike turnBitOff and turnBitOn, this method will always return a
     * RPGBitmask that is different from the RPGBitmask it was based on.<br>
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31, inclusive.
     * @throws IllegalArgumentException if the passed number is out of range.
     * @return A new RPGBitMask object.
     */
    public RPGBitmask toggleBit(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return new RPGBitmask(bits ^ (1 << toggle));
    }

    /**
     * Checks to see if the specified bit is on or off.
     * 
     * @param check The bit to check. Must be between 0 and 31.
     * @throws IllegalArgumentException if the passed number is out of range.
     * @return True if the bit is 1, false if the bit is 0.
     */
    public boolean checkBit(final int check)
    {
        if (check < 0 || check > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return (bits & 1 << check) == (1 << check);
    }

    /**
     * The hashCode of a RPGBitmask is equal to the number represented by the stored
     * booleans. That is, if the 5 and 6 bits are on and all other bits are off, the hashcode
     * is equal to ((2^6) + (2^5)), 96. <br>
     * If you want to compare two RPGBitmasks, for equality use {@link #equals(Object)}.<br>
     * If you want a String representation of the bits, use {@link #toString()}<br>
     * This method <b>does not</b> return direct reference to the stored integer.
     */
    @Override
    public int hashCode()
    {
        return new Integer(bits).intValue();
    }

    /**
     * Checks for the equality of two objects. Two RPGBitMasks are equal if and only if all of
     * their bits match.
     * 
     * @param obj The object to compare to.
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
            return false;

        if (obj instanceof RPGBitmask)
        {
            RPGBitmask temp = new RPGBitmask(((RPGBitmask) obj).bits ^ bits);

            if (temp.bits == 0)
                return true;
        }
        return false;
    }

    /**
     * Returns a a string of 1's and 0's. If a value is 1 it is true in the RPGBitmask. If
     * it is 0, then it is false.<br>
     * If you want the number represented by the boolean values, use {@link #hashCode()}
     * instead.
     */
    @Override
    public String toString()
    {
        StringBuilder temp = new StringBuilder();

        for (int i = 31; i >= 0; i--)
        {
            if (checkBit(i))
            {
                temp.append(1);
            }
            else
            {
                temp.append(0);
            }
        }
        return temp.toString();
    }
}
