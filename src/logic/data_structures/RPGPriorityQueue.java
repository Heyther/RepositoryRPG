
package logic.data_structures;

/**
 * Specifies methods for an RPGPriorityQueue.
 * 
 * @author Robert Ferguson: Primary coder.
 *
 * @param <T> The object that the queue holds. Must implement comparable. compareTo is assumed
 *            to return a negative number of the object comparing is smaller than the object it
 *            is being compared to.
 */
public interface RPGPriorityQueue <T>
{
    /**
     * Adds a new element to the queue.
     * 
     * @param element The element to add.
     */
    public abstract void add(T element);

    /**
     * Removes and returns the item in the queue with the most priority. Brings the next item
     * up to be removed.
     */
    public abstract T remove();

    /**
     * The size of the RPGPriorityQueue.
     * 
     * @return How many elements in the RPGPriorityQueue.
     */
    public abstract int size();

    /**
     * Whether or not the queue is empty. If this returns true, size() should also return 0.
     * 
     * @return True if the queue is empty, false otherwise.
     */
    public abstract boolean isEmpty();
}
