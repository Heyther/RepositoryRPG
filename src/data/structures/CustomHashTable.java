
package data.structures;

// import java.math.BigDecimal;
// import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
 * Ian Cresse and Robert Ferguson
 * TCSS 342 Data Structures
 * Spring 2015
 * HW 4
 */

/**
 * An implementation of a HashMap/Table for TCSS 342.
 * Included in the project intended to hold enemy data for random encounters and boss fights.
 * 
 * @author Robert Ferguson Primary coder.
 * @author Ian Cresse Code review.
 *
 * @param <K> The keys.
 * @param <V> The values.
 * 
 * @version v .5 May 12th 2015. Implemented basic putting and getting.
 * @version v 1.0 May 12th 2015. Refined basic putting and getting, working on double hashing.
 * @version v 2.0 May 18th 2015. Corrected an error with doubleHash that would occasionally
 *          cause it to return 0, which would cause an infinite loop while the map was
 *          attempting to find a proper place for the associated item. The doubleHash method
 *          should now always return at least 1.
 * @version v 2.5 Commented out methods that were necessary for TCSS 342, but are not for an
 *          RPG.
 */
public class CustomHashTable <K, V>
{
    // private final static int STATS_PRECISION = 6;

    private int size;
    private int entryCount;
    private List<Entry> table;

    /**
     * Creates a new hash table with capacity number of buckets. K is the type of Key and V is
     * the associated Value.
     * 
     * @param capacity
     */
    public CustomHashTable(int capacity)
    {
        size = capacity;
        entryCount = 0;
        table = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++)
        {
            table.add(null); // Fill the array list with nulls so it's considered of the
                             // correct size.
        }
    }

    /**
     * A private method that takes a key and returns an integer in the range [0, capacity)<br>
     * -Deterministic: A given K should always have the same hashValue<br>
     * -Uniformity: Each number in a range should have roughly equal probability.<br>
     * -Distribution: Numbers should avoid clustering.
     * 
     * @param searchKey the object to hash.
     * @return An integer for this object.
     */
    private int hash(K searchKey)
    {
        int hash = searchKey.hashCode();
        // hash ^= (hash >>> 3) ^ (hash >>> 22);
        hash = Math.abs((hash ^ (hash >>> 4) ^ (hash >>> 20)));
        return hash % size;
    }

    /**
     * A private method that takes a key and returns an integer in the range (0, capacity).
     * Note that unlike the primary hash, this method is unable to return 0.<br>
     * -Deterministic: A given K should always have the same hashValue<br>
     * -Uniformity: Each number in a range should have roughly equal probability.<br>
     * -Distribution: Numbers should avoid clustering.
     * 
     * @param searchKey the object to hash.
     * @return An integer for this object.
     */
    private int doubleHash(K searchKey)
    {
        int secondHash = searchKey.hashCode();
        // secondHash ^= (secondHash >> 7) ^ (secondHash >> 9);
        secondHash = Math.abs((secondHash ^ (secondHash >> 4) ^ (secondHash >> 12)));
        secondHash %= size;
        // System.out.println(secondHash);
        while (secondHash < 1)
            secondHash++;

        return secondHash;
    }

    /**
     * Updates or adds the newValue to the bucket hash(searchKey). If hash(key) is full use
     * Linear probing to find the next available bucket.
     * 
     * @param searchKey The key that goes in the map. Must be unique.
     * @param newValue The value that goes into the map associated with this key.
     * @throws IllegalArgumentException If the list would be too full to accomodate the new
     *             entry.
     */
    public void put(K searchKey, V newValue) throws IllegalArgumentException
    {
        if (entryCount == size)
        {
            throw new IllegalArgumentException(
                    "Adding another element would cause the map to be too full.");
        }

        int hashcode = hash(searchKey);
        int next = doubleHash(searchKey);
        // int steps = 0;

        // System.out.println("Hash values for " + searchKey.toString() + ": " + hashcode
        // + " and " + next);

        // Entry temp = new Entry(hashcode, searchKey, newValue);

        // Same thing as getting, we need to search for the same Key, or the next available
        // node
        if (table.get(hashcode) == null)
        {
            // System.out.println("Steps in put " + searchKey.toString() + ": " + steps);
            table.set(hashcode, (new Entry(searchKey, newValue)));
            // table.set(hashcode, new Entry(steps, searchKey, newValue));
            entryCount++;
        }
        else
        {
            while (table.get(hashcode) != null && !table.get(hashcode).key.equals(searchKey))
            {
                // System.out.println("Clashed with: " + table.get(hashcode));
                hashcode = (hashcode + next) % size;
                // steps++;
            }
            // System.out.println("Steps in put " + searchKey.toString() + ": " + steps);
            if (table.get(hashcode) == null)
                entryCount++;
            table.set(hashcode, (new Entry(searchKey, newValue)));
            // table.set(hashcode, (new Entry(steps, searchKey, newValue)));
        }
    }

    /**
     * Return a value for the specified key from the bucket hash(searchKey). If hash(searchKey)
     * doesn't contain the value, linear probing is used to return the correct value.
     * 
     * @param searchKey The key to search with.
     * @return the value to which the specified key is mapped, or null if this map contains no
     *         mapping for the key.
     * @throws NullPointerException if the specified key is null or is not in the map.
     */
    public V get(K searchKey)
    {
        int searchHash = hash(searchKey);
        // int steps = 0;

        if (table.get(searchHash).key.equals(searchKey))
        {
            // System.out.println("Steps in get " + searchKey.toString() + ": " + steps);
            return table.get(searchHash).value;
        }
        // System.out.println("Bumped into: " + table.get(searchHash));

        int stop = searchHash;
        int incrementHash = doubleHash(searchKey);

        do
        {
            searchHash = (searchHash + incrementHash) % size;
            if (table.get(searchHash).key.equals(searchKey))
            {
                // System.out.println("Steps in get " + searchKey.toString() + ": " + steps);
                return table.get(searchHash).value;
            }
            // System.out.println("Bumped into: " + table.get(searchHash));
            // steps++;
        } while (table.get(searchHash) != null && searchHash != stop);

        // The key was never found, so return a null and let them deal with it.
        // System.out.println("Steps in get " + searchKey.toString() + ": " + steps);
        return null;
    }

    /**
     * Returns true if the key is in the map.
     * 
     * @param searchKey The key to check for.
     * @return True if the key is found, false otherwise.
     */
    public boolean containsKey(K searchKey)
    {
        int searchHash = hash(searchKey);

        if (table.get(searchHash) != null && table.get(searchHash).key.equals(searchKey))
        {
            // found it immediately
            return true;
        }

        // prepare to find it
        int stop = searchHash;
        int incrementHash = doubleHash(searchKey);

        // Probe until we find a null entry or we've gone over the entire table.
        do
        {
            searchHash = (searchHash + incrementHash) % size;
            // perform a null check before looking at fields. Because of how the && operator
            // works in Java, if the node is null, we will never look at it's fields, which
            // allows us to avoid null pointer exceptions.
            if (table.get(searchHash) != null && table.get(searchHash).key.equals(searchKey))
            {
                // found it eventually
                return true;
            }
        } while (table.get(searchHash) != null && searchHash != stop);

        // didn't find it
        return false;
    }

    /**
     * A function that displays the stat block in the hash table. This includes number of
     * entities, capacity, a histogram of the probes, the fill percentage, the maximum probing
     * to be done and the average probing to be done.<br>
     * The table is iterated over twice: Once to check what the highest amount anything had to
     * move was and another to count each time something was moved n times.<br>
     * <b>Prints output to the console.</b>
     */
    /*
     * public void stats()
     * {
     * StringBuilder ts = new StringBuilder();
     * ts.append("Hash Table Stats\n=================\n");
     * ts.append("Number of entries: " + entryCount + "\n");
     * ts.append("Number of buckets: " + size + "\n");
     * ts.append("Histogram of probes: \n");
     * ts.append('[');
     * // int q = 0;
     * int highest = 0;
     * double average = 0;
     * for (int i = 0; i < size; i++)
     * {
     * if (table.get(i) != null)
     * {
     * // ts.append(table.get(i).moved + ", ");
     * // q++;
     * average += table.get(i).moved;
     * if (table.get(i).moved > highest)
     * highest = table.get(i).moved;
     * // if (q % 16 == 0)
     * // {
     * // ts.append('\n');
     * // }
     * }
     * }
     * int[] histogram = new int[highest + 1];
     * for (int i = 0; i < size; i++)
     * {
     * if (table.get(i) != null)
     * {
     * histogram[table.get(i).moved]++;
     * }
     * }
     * for (int i = 0; i < histogram.length; i++)
     * {
     * ts.append(histogram[i]);
     * if (i < histogram.length - 1)
     * {
     * ts.append(',');
     * }
     * if ((i + 1) % 16 == 0) // we want up to x entries per line
     * {
     * ts.append('\n');
     * }
     * }
     * // chop off the last " ,"
     * // ts.setLength(ts.length() - 2);
     * ts.append(']');
     * // prints out the fill rate with the decimal moved right by 2 so it can be
     * // output as a percentage.
     * BigDecimal fillRate = new BigDecimal(entryCount);
     * fillRate = fillRate.divide(new BigDecimal(size), STATS_PRECISION,
     * RoundingMode.HALF_EVEN);
     * ts.append("\nFill percentage: " + fillRate.movePointRight(2).toString() + "%");
     * ts.append("\nMax probe: " + highest);
     * ts.append("\nAverage probe: " + (average / entryCount));
     * System.out.println(ts.toString());
     * }
     */

    /**
     * Gives the keys in the HashTable as a TreeSet.
     * 
     * @return A TreeSet object containing all the keys, but not the associated values.
     */
    public Set<K> keySet()
    {
        Set<K> temp = new TreeSet<>();
        for (int i = 0; i < size; i++)
        {
            if (table.get(i) != null)
            {
                temp.add(table.get(i).key);
            }
        }
        return temp;
    }

    /**
     * Returns the number of elements currently in the Hash Table.
     * 
     * @return The number of elements.
     */
    public int getSize()
    {
        return entryCount;
    }

    /**
     * Converts the hash map contents to a string. Keys and values are responsible for
     * providing their own toString() method.
     */
    @Override
    public String toString()
    {
        boolean afterFirst = false;
        StringBuilder ts = new StringBuilder();
        ts.append('{');

        for (int i = 0; i < size; i++)
        {
            if (table.get(i) != null)
            {
                if (afterFirst)
                {
                    ts.append(", " + table.get(i).toString());
                }
                else
                {
                    afterFirst = true;
                    ts.append(table.get(i).toString());
                }

            }
        }

        // for (Entry e : table)
        // {
        // if (e != null)
        // ts.append(' ' + e.toString() + ',');
        // }
        ts.append('}');

        return ts.toString();
    }

    private class Entry
    {
        private K key;
        private V value;

        // private int moved;
        /*
         * private Entry(final int m, final K k, final V v)
         * {
         * key = k;
         * value = v;
         * moved = m;
         * }
         */

        private Entry(final K k, final V v)
        {
            key = k;
            value = v;
        }

        public String toString()
        {
            return key.toString() + "=" + value.toString();
        }
    }
}
