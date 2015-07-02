
package logic.data_structures;

// import java.math.BigDecimal;
// import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
public class RPGHashTable <K, V>
{
    // private final static int STATS_PRECISION = 6;

    private int size;
    private int entryCount;
    private List<Entry> table;
    private float load;
    private static final float MAX_LOAD = .75f;

    /**
     * Creates a new hash table with 2^10 (1024) buckets. K is the type of Key and V is the
     * associated value.
     */
    public RPGHashTable()
    {
        this(1024);
    }

    /**
     * Creates a new hash table with capacity number of buckets. K is the type of Key and V is
     * the associated Value.
     * 
     * @param capacity
     */
    public RPGHashTable(int capacity)
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
        hash ^= (hash >> 3) ^ (hash >> 22);
        hash = Math.abs((hash ^ (hash >> 4) ^ (hash >> 20)));
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
        secondHash ^= (secondHash >> 4) ^ (secondHash >> 9);
        secondHash = Math.abs((secondHash ^ (secondHash >> 12) ^ (secondHash >> 7)));

        while (secondHash < 1)
            secondHash++;

        return secondHash % size;
    }

    /**
     * Updates or adds the newValue to the bucket hash(searchKey).
     * 
     * @param searchKey The key that goes in the map. Must be unique as keys that are already
     *            in the map will be overriden by this method.
     * @param newValue The value that goes into the map associated with this key.
     */
    public void put(K searchKey, V newValue)
    {
        if (load >= MAX_LOAD)
        {
            rehash();
        }

        int hashcode = hash(searchKey);
        int next = doubleHash(searchKey);

        // Same thing as getting, we need to search for the same Key, or the next available
        // node
        if (table.get(hashcode) == null)
        {
            table.set(hashcode, (new Entry(searchKey, newValue)));
            entryCount++;
        }
        else
        {
            while (table.get(hashcode) != null && !table.get(hashcode).key.equals(searchKey))
            {
                hashcode = (hashcode + next) % size;
            }

            if (table.get(hashcode) == null)
                entryCount++;
            table.set(hashcode, (new Entry(searchKey, newValue)));
        }

        load = (float) entryCount / size;
    }

    private void rehash()
    {
        List<Entry> temp = new ArrayList<>();

        for (int i = 0; i < size; i++)
        {
            if (table.get(i) != null)
            {
                temp.add(table.get(i));
            }
        }
        table.clear();

        for (int i = 0; i < (size * 2); i++)
        {
            table.add(null); // Fill the array list with nulls so it's considered of the
                             // correct size.
        }
        size *= 2;

        for (Entry e : temp)
        {
            int hashcode = hash(e.key);
            int next = doubleHash(e.key);

            if (table.get(hashcode) == null)
            {
                table.set(hashcode, e);
            }
            else
            {
                while (table.get(hashcode) != null && !table.get(hashcode).key.equals(e.key))
                {
                    hashcode = (hashcode + next) % size;
                }

                if (table.get(hashcode) == null)
                    entryCount++;
                table.set(hashcode, e);
            }
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
     * Converts the hash map contents to a String. Keys and values are responsible for
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
            return key.toString() + "->" + value.toString();
        }
    }
}
