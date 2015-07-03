
package tests;

import static org.junit.Assert.*;

import java.util.Random;

import logic.data_structures.RPGHashTable;

import org.junit.Before;
import org.junit.Test;

public class RPGHashTableTest
{

    private RPGHashTable<String, String> testHash = new RPGHashTable<>();

    @Before
    public void setUp() throws Exception
    {
        testHash = new RPGHashTable<String, String>();
    }

    @Test
    public void testRPGHashTable()
    {
        assertTrue(testHash != null);
    }

    @Test
    public void testGetAndPut()
    {
        testHash.get(null);
        testHash.containsKey(null);
        testHash.containsKey("Get");
        testHash.get("Key");
        testHash.put("Key", "Value");
        assertTrue(testHash.get("Key").equals("Value"));
    }

    @Test
    public void testRehash()
    {
        testHash = new RPGHashTable<String, String>();
        StringBuilder garbageKey = new StringBuilder();
        StringBuilder garbageValue = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 8000; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                garbageKey.append(rand.nextInt(128));
            }
            for (int k = 0; k < 10; k++)
            {
                garbageValue.append(rand.nextInt(128));
            }

            testHash.put(garbageKey.toString(), garbageValue.toString());

            garbageKey.setLength(0);
            garbageValue.setLength(0);
        }

        assertTrue(true); // if we got here, we didn't crash.
    }
}
