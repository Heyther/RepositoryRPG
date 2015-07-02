
package tests;

import static org.junit.Assert.*;
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
        testHash = new RPGHashTable<String, String>(16);
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
        testHash = new RPGHashTable<String, String>(4);

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
