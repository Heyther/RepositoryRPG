
package data_structures;

/**
 * A class which allows 32 boolean values, which would normally take 8*32 bytes, to be stored
 * in the space of a single integer, 8 bytes.
 * This is an immutable class.
 * 
 * @author Robert Ferguson.
 * @version 1.0 June 29th 2015
 *          Created class.
 * @version 1.1 June 30th 2015
 *          Added class constants to create a new CustomBitmask object, which are intended ot
 *          be used instead of the constructor.<br>
 *          Added CustomBitMask toggleBit(toggle).<br>
 *          Updated documentation.<br>
 *          Implemented equals(Object obj), and hashcode() and added documentation.<br>
 */
public final class CustomBitmask
{
    /** The bits storing the booleans. */
    private final int bits;

    /** A CustomBitmask with all the booleans set to false. */
    public final static CustomBitmask ALL_FALSE = new CustomBitmask(0);

    /** A CustomBitmask with all the booleans set to true. */
    public final static CustomBitmask ALL_TRUE = new CustomBitmask(~0);

    /**
     * Creates a new CustomBitMask with the bits all initially set to 0.
     * 
     * @Deprecated Use the ALL_FALSE or ALL_TRUE class constants instead.
     */
    public CustomBitmask()
    {
        this(0);
    }

    /**
     * Creates a new CustomBitMask with the bits being equal to the
     * bits from the passed CustomBitMask.
     * 
     * @param theBits
     */
    private CustomBitmask(final int theBits)
    {
        bits = theBits;
    }

    /**
     * Returns a new CustomBitMask object with the specified bit turned on. Has no effect if
     * the bit is already on.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31, inclusive.
     * @throws IllegalArgumentException if the number is out of range.
     * @return A new CustomBitMask object.
     */
    public CustomBitmask turnBitOff(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return new CustomBitmask(bits & ~(1 << toggle));
    }

    /**
     * Returns a new CustomBitMask object with the specified bit turned off. Has no effect
     * if the bit is already off.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31, inclusive.
     * @throws IllegalArgumentException if the number is out of range.
     * @return A new CustomBitMask object.
     */
    public CustomBitmask turnBitOn(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return new CustomBitmask(bits | (1 << toggle));
    }

    /**
     * Returns a new CustomBitMask object with the specified bit toggled. That is, if the bit
     * is currently on, it is turned off and if the bit is currently off, it is turned on.
     * 
     * Note that unlike turnBitOff and turnBitOn, this method will always return a
     * CustomBitmask that is different from the object it was based on.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31, inclusive.
     * @throws IllegalArgumentException if the number is out of range.
     * @return A new CustomBitMask object.
     */
    public CustomBitmask toggleBit(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        if (checkBit(toggle))
            return turnBitOff(toggle);
        else
            return turnBitOn(toggle);
    }

    /**
     * Checks to see if the specified bit is on or off.
     * 
     * @param check The bit to check. Must be between 0 and 31.
     * @return True if the bit is 1, false if the bit is 0.
     */
    public boolean checkBit(final int check)
    {
        if (check < 0 || check > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return (bits & 1 << check) == (1 << check);
    }

    /**
     * Returns a new CustomBitMask object that is reset to the initial values. Functionally
     * similar to using the new keyword on a CustomBitMask.
     * 
     * @return A CustomBitMask object that is entirely false.
     */
    public CustomBitmask reset()
    {
        return new CustomBitmask(0);
    }

    /**
     * The hashCode of a CustomBitmask is equal to the number represented by the stored
     * booleans. That is, if the 5 and 6 bits are on, the hashcode is equal to (2^6) plus
     * (2^5). <br>
     * This method does not return direct reference to the stored bits.
     */
    @Override
    public int hashCode()
    {
        return new Integer(bits).intValue();
    }

    /**
     * Two CustomBitMasks are equal if and only if all of their bits match.
     * 
     * @param The object to compare to.
     */
    @Override
    public boolean equals(final Object obj)
    {
        // A null object is not a CustomBitmask
        if (obj == null)
            return false;

        if (obj instanceof CustomBitmask)
        {
            /*
             * Any bit XOR itself is equal to 0:
             * 1 XOR 1 = 0
             * 0 XOR 0 = 0
             * Any bit XOR not itself is equal to 1.
             * 0 XOR 1 = 1
             * 1 XOR 0 = 1
             * Because of this, any string of bits XOR itself would have 0's at every location.
             */
            CustomBitmask temp = new CustomBitmask(((CustomBitmask) obj).bits ^ bits);

            if (temp.bits != 0)
                return false;
            else
                return true;
        }
        else
            // The obj wasn't a CustomBitmask at all, and the two cannot be equal.
            return false;
    }

    /**
     * Returns a a string of 1's and 0's. If a value is 1 it is true in the CustomBitmask. If
     * it is 0, then it is false.
     * 
     * If you want the number represented by the boolean values, use {@link #hashCode()}
     * instead.
     */
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
