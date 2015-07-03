
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
    public void testRPGHashTableInt()
    {
        testHash = new RPGHashTable<String, String>();
        assertTrue(testHash != null);
    }

    @Test
    public void testPut()
    {
        testHash.put("Key", "Value");
        assertTrue(testHash.getSize() == 1);
    }

    @Test
    public void testGet()
    {
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

        testHash.put("Key", "Value");
        testHash.put("Ashley", "Chambers");
        testHash.put("Robert", "Ferguson");
        testHash.put("Ian", "Cresse");
        testHash.put("Wil", "Sunseri");
        testHash.put("Blank", "String");
        testHash.put("Seventh", "Entry");
        testHash.put("Should", "Expand Again");

        assertTrue(true); // if we got here, we didn't crash.
    }
}
