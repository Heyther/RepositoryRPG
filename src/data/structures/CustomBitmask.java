
package data.structures;

/**
 * A class which allows 64 boolean values, which would normally take 8*64 bytes, to be stored
 * in the space of a single long integer, 16 bytes.
 * This is an immutable class.
 * 
 * @author Robert Ferguson.
 *
 */
public class CustomBitmask
{
    /**
     * The bits storing the booleans.
     */
    private final int bits;

    /**
     * Creates a new CustomBitMasking with the bits all initially set to 0.
     */
    public CustomBitmask()
    {
        this(0);
    }

    /**
     * Private constructor. Creates a new CustomBitMasking with the bits being equal to the
     * bits from the passed CustomBitMasking.
     * 
     * @param theBits
     */
    private CustomBitmask(final int theBits)
    {
        bits = theBits;
    }

    /**
     * Returns a new CustomBitMasking object with the specified bit turned on. Has no effect if
     * the bit is already on.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31.
     * @throws IllegalArgumentException if the number is out of range.
     * @return A new CustomBitMasking object.
     */
    public CustomBitmask turnBitOff(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return new CustomBitmask(bits & ~(1 << toggle));
    }

    /**
     * Returns a new CustomBitMasking object with the specified bit turned off. Has no effect
     * if the bit is already off.
     * 
     * @param toggle The bit to toggle. Must be between 0 and 31.
     * @throws IllegalArgumentException if the number is out of range.
     * @return A new CustomBitMasking object.
     */
    public CustomBitmask turnBitOn(final int toggle)
    {
        if (toggle < 0 || toggle > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return new CustomBitmask(bits | (1 << toggle));
    }

    /**
     * Checks to see if the specified bit is on or off.
     * 
     * @param check The bit to check.
     * @return True if the bit is 1, false if the bit is 0.
     */
    public boolean checkBit(final int check)
    {
        if (check < 0 || check > 31)
            throw new IllegalArgumentException("You must specify a bit between 0 and 31.");

        return (bits & 1 << check) == (1 << check);
    }

    /**
     * Returns a new CustomBitMasking object that is reset to the initial values.
     * 
     * @return A CustomBitMasking object that is entirely false.
     */
    public CustomBitmask reset()
    {
        return new CustomBitmask(0);
    }

    /**
     * Returns a a string of 1's and 0's. If a value is 1 it is true in the CustomBitmask. If
     * it is
     * 0, then it is false.
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
