
package tests;

import static org.junit.Assert.*;
import logic.data_structures.RPGMaxPriorityQueue;
import logic.data_structures.RPGPriorityQueue;

import org.junit.Before;
import org.junit.Test;

public class RPGMaxPriorityQueueTest
{

    public RPGPriorityQueue<Integer> testQueue = new RPGMaxPriorityQueue<>();

    @Before
    public void setUp() throws Exception
    {
        testQueue = new RPGMaxPriorityQueue<>();
    }

    @Test
    public void testCustomPriorityQueue()
    {
        assertTrue(testQueue != null);
    }

    @Test
    public void testAdd()
    {
        testQueue.add(1);
        testQueue.add(5);

        assertTrue(testQueue.size() == 2);
    }

    @Test
    public void testRemove()
    {
        testQueue.add(1);
        testQueue.add(5);
        testQueue.add(202);
        testQueue.add(203);
        testQueue.add(200);
        testQueue.add(201);

        assertTrue(testQueue.remove() == 203);
        assertTrue(testQueue.remove() == 202);
        assertTrue(testQueue.remove() == 201);
        assertTrue(testQueue.remove() == 200);
        assertTrue(testQueue.remove() == 5);
        assertTrue(testQueue.remove() == 1);
    }

    @Test
    public void testIsEmpty()
    {
        assertTrue(testQueue.isEmpty());
    }
}
