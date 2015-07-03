
package logic.data_structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * <s>An implementation of a HashMap/Table for TCSS 342.</s><br>
 * An extendible HashTable. Able to take key/value pairs and store them as entries that can be
 * retrieved later, generally in O(1) time. Keys are responsible for providing their own
 * functional hashcodes. Failure to do so will result in high collisions, and therefore slower
 * putting and getting of elements.<br>
 * This HashTable <b>does not</b> support removal of keys.<br>
 * 
 * @param <K> The keys.
 * @param <V> The values.
 * 
 * @version <b>0.5</b> <u>May 12th 2015</u> Implemented basic putting and getting.<br>
 *          <b>1.0</b> <u>May 12th 2015</u> Refined putting and getting, working on
 *          double hashing. <br>
 *          <b>2.0</b> <u>May 18th 2015</u>. Corrected an error with doubleHash that would
 *          occasionally cause it to return 0, which would cause an infinite loop while the map
 *          was attempting to find a proper place for the associated item. The doubleHash
 *          method should now always properly return at least 1. <br>
 *          <b>2.1</b> <u>June 30th</u> Commented out informational methods that were
 *          necessary for TCSS 342, but are not for an RPG.<br>
 *          <b>3.0</b> <u>July 2nd 2015</u><br>
 *          Renamed hash(K searchKey) to primaryHash(K searchKey)
 *          Renamed doubleHash(K searchKey) to secondaryHash(K searchKey)
 *          Modified secondaryHash(K searchKey) to be slightly faster.<br>
 *          Rewrote documentation to be more consistent with other logic.data_structures
 *          classes.<br>
 *          Changed some internal variable names to be more clear.<br>
 *          Reorganized methods internally.<br>
 * 
 * @author Robert Ferguson: Primary coder.
 * @author Ian Cresse: Code review for versions 0.5 through 2.0.
 * 
 */
public final class RPGHashTable <K, V> implements Serializable
{
    /** Serial Version UID */
    private static final long serialVersionUID = -4698366790849747127L;

    /** The max number of objects the RPGHashTable is currently able to hold. */
    private int tableSize;

    /** The current number of objects the RPGHashTable is currently holding. */
    private int currentNumEntries;

    /** The table holding the hashed objects. */
    private List<Entry> table;

    /** The current load of the table. */
    private float currentLoad;

    /** The maximum load before the table is resized itself. */
    private static final float MAX_LOAD = .75f;

    /**
     * Creates a new hash table with 2^10 (1024) buckets. K is the type of Key and V is the
     * associated value.
     */
    public RPGHashTable()
    {
        tableSize = 1024;
        currentNumEntries = 0;
        table = new ArrayList<>(tableSize);
        for (int i = 0; i < tableSize; i++)
        {
            table.add(null);
            // Fill the array list with nulls so it's considered of the correct size.
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
    private int primaryHash(final K searchKey)
    {
        int hash = searchKey.hashCode();
        hash ^= (hash >> 3) ^ (hash >> 22);
        hash = ~(1 << 31) & ((hash ^ (hash >> 4) ^ (hash >> 20)));
        return hash % tableSize;
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
    private int secondaryHash(final K searchKey)
    {
        int secondHash = searchKey.hashCode();
        secondHash ^= (secondHash >> 4) ^ (secondHash >> 9);
        secondHash = ~(1 << 31) & ((secondHash ^ (secondHash >> 12) ^ (secondHash >> 7)));

        return (secondHash % tableSize) | 1;
    }

    /**
     * 
     * Adds the passed value to the bucket hash(key). If there is already a value in bucket
     * hash(key), then the structure will be probed until a proper bucket is found.
     * 
     * @param key The key that goes in the map. Must be unique as keys that are already
     *            in the map will be overriden by this method.
     * @param value The value that goes into the map associated with this key.
     */
    public void put(final K key, final V value)
    {
        if (currentLoad >= MAX_LOAD)
        {
            rehashTable();
        }

        int hashcode = primaryHash(key);
        int next = secondaryHash(key);

        // Same thing as getting, we need to search for the same Key, or the next available
        // node
        if (table.get(hashcode) == null)
        {
            table.set(hashcode, (new Entry(key, value)));
            currentNumEntries++;
        }
        else
        {
            while (table.get(hashcode) != null && !table.get(hashcode).key.equals(key))
            {
                hashcode = (hashcode + next) % tableSize;
            }

            if (table.get(hashcode) == null)
                currentNumEntries++;
            table.set(hashcode, (new Entry(key, value)));
        }

        currentLoad = (float) currentNumEntries / tableSize;
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
    public V get(final K searchKey)
    {
        int searchHash = primaryHash(searchKey);
        // int steps = 0;

        if (table.get(searchHash).key.equals(searchKey))
        {
            // System.out.println("Steps in get " + searchKey.toString() + ": " + steps);
            return table.get(searchHash).value;
        }
        // System.out.println("Bumped into: " + table.get(searchHash));

        int stop = searchHash;
        int incrementHash = secondaryHash(searchKey);

        do
        {
            searchHash = (searchHash + incrementHash) % tableSize;
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
    public boolean containsKey(final K searchKey)
    {
        int searchHash = primaryHash(searchKey);

        if (table.get(searchHash) != null && table.get(searchHash).key.equals(searchKey))
        {
            // found it immediately
            return true;
        }

        // prepare to find it
        int stop = searchHash;
        int incrementHash = secondaryHash(searchKey);

        // Probe until we find a null entry or we've gone over the entire table.
        do
        {
            searchHash = (searchHash + incrementHash) % tableSize;
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
     * Extends and rehashes the table.
     */
    private void rehashTable()
    {
        List<Entry> temp = new ArrayList<>();

        for (int i = 0; i < tableSize; i++)
        {
            if (table.get(i) != null)
            {
                temp.add(table.get(i));
            }
        }
        table.clear();

        for (int i = 0; i < (tableSize * 2); i++)
        {
            table.add(null); // Fill the array list with nulls so it's considered of the
                             // correct size.
        }
        tableSize *= 2;

        for (Entry e : temp)
        {
            int hashcode = primaryHash(e.key);
            int next = secondaryHash(e.key);

            if (table.get(hashcode) == null)
            {
                table.set(hashcode, e);
            }
            else
            {
                while (table.get(hashcode) != null && !table.get(hashcode).key.equals(e.key))
                {
                    hashcode = (hashcode + next) % tableSize;
                }

                if (table.get(hashcode) == null)
                    currentNumEntries++;
                table.set(hashcode, e);
            }
        }
    }

    /**
     * Gives the keys in the HashTable as a TreeSet.
     * 
     * @return A TreeSet object containing all the keys, but not the associated values.
     */
    public Set<K> keySet()
    {
        Set<K> temp = new TreeSet<>();
        for (int i = 0; i < tableSize; i++)
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
        return currentNumEntries;
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

        for (int i = 0; i < tableSize; i++)
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

    private final class Entry
    {
        private final K key;
        private final V value;

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
