
package tests;

import static org.junit.Assert.*;
import logic.data_structures.RPGBitmask;

import org.junit.Before;
import org.junit.Test;

public class RPGBitmaskTest
{
    public RPGBitmask testMask = RPGBitmask.ALL_FALSE;

    @Before
    public void setUp() throws Exception
    {
        testMask = RPGBitmask.ALL_FALSE;
    }

    @Test
    public void testHashCode()
    {
        assertEquals(0, testMask.hashCode());
        assertNotEquals(1, testMask.hashCode());
        testMask = testMask.toggleBit(0);
        assertEquals(1, testMask.hashCode());
    }

    @Test
    public void testCustomBitmask()
    {
        assertEquals(new RPGBitmask(), testMask);
    }

    @Test
    public void testTurnBitOff()
    {
        testMask = testMask.turnBitOn(0);
        testMask = testMask.turnBitOff(0);
        testMask = testMask.turnBitOff(0);
        testMask = testMask.turnBitOff(30);

        assertEquals(new RPGBitmask(), testMask);
    }

    @Test
    public void testTurnBitOn()
    {
        assertEquals(new RPGBitmask().turnBitOn(1), testMask.turnBitOn(1));
        assertNotEquals(new RPGBitmask(), testMask.turnBitOn(13));
    }

    @Test
    public void testToggleBit()
    {
        // turn bit on with toggle
        testMask = testMask.toggleBit(0);
        testMask = testMask.turnBitOff(0);

        assertEquals(new RPGBitmask(), testMask);

        // turn bit off with toggle
        testMask = testMask.turnBitOn(0);
        testMask = testMask.toggleBit(0);

        assertEquals(new RPGBitmask(), testMask);
    }

    @Test
    public void testCheckBit()
    {
        testMask = testMask.toggleBit(1);
        assertTrue(testMask.checkBit(1));
    }

    @Test
    public void testReset()
    {
        for (int i = 0; i < 31; i++)
        {
            testMask = testMask.toggleBit(1);
        }

        testMask = RPGBitmask.ALL_FALSE;

        assertEquals(new RPGBitmask(), testMask);
    }

    @Test
    public void testEqualsObject()
    {
        assertFalse(testMask.equals(null));
        assertTrue(testMask.equals(new RPGBitmask()));

        testMask = testMask.toggleBit(4);
        assertFalse(testMask.equals(new RPGBitmask()));
        assertFalse(testMask.equals("Not a CustomBitmask"));
    }

    @Test
    public void testToString()
    {
        assertEquals("00000000000000000000000000000000", testMask.toString());

        testMask = testMask.turnBitOn(0);
        testMask = testMask.turnBitOn(31);

        assertEquals("10000000000000000000000000000001", testMask.toString());
    }
}
