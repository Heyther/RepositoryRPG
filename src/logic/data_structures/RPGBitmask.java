
package logic.data_structures;

/**
 * An immutable class which allows 32 boolean values to be stored in the space of a single
 * integer, 8 bytes.
 * 
 * @version <b>1.0</b> <u>June 29th 2015</u><br>
 *          Created class and wrote initial methods.<br>
 *          <b>1.1</b> <u>June 30th 2015</u><br>
 *          Added class constants for a new RPGBitmask object, which are intended to
 *          be used instead of the constructor.<br>
 *          Deprecated the public, no argument constructor.<br>
 *          Added method RPGBitMask toggleBit(toggle). More information can be found in it's
 *          javadoc. <br>
 *          Updated documentation of most methods.<br>
 *          Implemented equals(Object obj), and hashcode() and added documentation.<br>
 *          <b>1.1b</b> <u>July 1st 2015</u><br>
 *          Renamed class to RPGBitmask. All methods updated to return an RPGBitmask object.<br>
 *          Corrected grammar and spelling errors in some documentation.<br>
 * @author Robert Ferguson: Primary Coder.
 */
public final class RPGBitmask
{
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
     * Creates a new RPGBitMask with the bits being equal to the bits from a different
     * RPGBitMask.
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

        /*
         * The code below:
         * toggle = 4;
         * 1 : 0000 0000 0000 0000 0000 0000 0000 0001
         * << 4: 0000 0000 0000 0000 0000 0000 0001 0000
         * ~ : 1111 1111 1111 1111 1111 1111 1110 1111
         * 1 & 1 = 1
         * 0 & 1 = 0
         * All 0's remain as 0.
         * All 1's remain as 1.
         * If the toggled bit is 1, it will turn to 0.
         * If the toggled bit is 0, it will remain 0.
         * Therefore: the specified bit will always be 0.
         */
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
        /*
         * The code below:
         * toggle = 8;
         * 1 : 0000 0000 0000 0000 0000 0000 0000 0001
         * << 8: 0000 0000 0000 0000 0000 0001 0000 0000
         * 1 | 1 = 1
         * 0 | 1 = 1
         * 0 | 0 = 0
         * If the toggled bit is 1, it will remain 1.
         * If the toggled bit is 0, it turn in to 1.
         * Therefore: the specified bit will always be 1.
         * Other bits, whether they are 0 or 1, are unaffected by the 0's in the rest of the
         * bit string.
         */

        return new RPGBitmask(bits | (1 << toggle));
    }

    /**
     * Returns a new RPGBitMask object with the specified bit toggled.<br>
     * That is, if the bit is currently on, it is turned off and if the bit is currently off,
     * it is turned on.<br>
     * <br>
     * Note that unlike turnBitOff and turnBitOn, this method will always return a
     * RPGBitmask that is different from the object it was based on.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31, inclusive.
     * @throws IllegalArgumentException if the passed number is out of range.
     * @return A new RPGBitMask object.
     */
    public RPGBitmask toggleBit(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        /*
         * See the comments within the used methods to see how they work.
         */

        if (checkBit(toggle))
            return turnBitOff(toggle);
        else
            return turnBitOn(toggle);
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
     * Returns a new RPGBitMask object that is reset to the initial values. Functionally
     * similar to using the new keyword on a RPGBitMask.
     * 
     * @return A RPGBitMask object that is entirely false.
     */
    public RPGBitmask reset()
    {
        return new RPGBitmask(0);
    }

    /**
     * The hashCode of a RPGBitmask is equal to the number represented by the stored
     * booleans. That is, if the 5 and 6 bits are on and all other bits are off, the hashcode
     * is equal to ((2^6) + (2^5)), 96. <br>
     * This method does not return direct reference to the stored integer.
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
        // A null object is not a RPGBitmask
        if (obj == null)
            return false;

        if (obj instanceof RPGBitmask)
        {
            /*
             * Any bit XOR itself is equal to 0:
             * 1 XOR 1 = 0
             * 0 XOR 0 = 0
             * Any bit XOR ~itself is equal to 1.
             * 0 XOR ~0 -> 0 XOR 1 = 1
             * 1 XOR ~1 -> 1 XOR 0 = 1
             * Because of this, any string of bits XOR itself will be entirely 0's.
             */
            RPGBitmask temp = new RPGBitmask(((RPGBitmask) obj).bits ^ bits);

            if (temp.bits != 0)
                return false;
            else
                return true;
        }
        else
            // The obj wasn't a RPGBitmask at all, and the two cannot be equal.
            return false;
    }

    /**
     * Returns a a string of 1's and 0's. If a value is 1 it is true in the RPGBitmask. If
     * it is 0, then it is false.<br>
     * <br>
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
