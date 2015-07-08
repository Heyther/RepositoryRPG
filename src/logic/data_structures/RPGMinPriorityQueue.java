
package logic.data_structures;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic priority queue that favors elements that compare negatively to other comparable
 * elements.
 *
 * @author Robert Ferguson Primary coder.
 * @author Ian Cresse Code Review
 * @param <T> The object that the queue holds. Must implement comparable. compareTo is assumed
 *            to return a negative number if an object is smaller than the object it
 *            is being compared to.
 * @version <b>2.0</b> <u>July 1st 2015</u><br>
 *          Began documenting changes.<br>
 *          <b>2.1</b> <u>July 2nd 2015</u><br>
 *          Wrote a constructor that takes an RPGPriorityQueue object.
 */
public final class RPGMinPriorityQueue <T extends Comparable<T>> implements
        RPGPriorityQueue<T>
{
    /** Serial Version UID */
    private static final long serialVersionUID = 2842481383751622386L;

    /** The list of objects. */
    private List<T> prio;

    /** The amount of objects currently in the list. */
    private int size;

    /**
     * Creates a new priorityQueue.
     */
    public RPGMinPriorityQueue()
    {
        prio = new ArrayList<T>();
    }

    /**
     * Creates a new RPGMaxPriorityQueue based on the passed RPGPriorityQueue of the same
     * generic type.<br>
     * Calls to this constructor should be done like this:<br>
     * example = new RPGMinPriorityQueue<Integer>((RPGMaxPriorityQueue<Integer>) example);<br>
     * Note that the passed queue must be cast to the type that it currently is.
     */
    public RPGMinPriorityQueue(final RPGPriorityQueue<T> oldQueue)
    {
        prio = new ArrayList<T>();

        while (!oldQueue.isEmpty())
            this.add(oldQueue.remove());

    }

    /**
     * Adds a new element to the queue.
     * 
     * The element is added to the end and then bubbles up to it's proper priority in the
     * queue.
     * 
     * @param element The element to add.
     */
    public void add(T element)
    {
        prio.add(size, element);
        bubbleUp(size++);
    }

    /**
     * Removes and returns the item in the queue with the most priority. Brings the next item
     * up to be removed.
     * 
     * This is accomplished by creating a reference to the top most (highest priority) element
     * and then swapping it to the place of the lowest priority element. It is then removed.
     * Since the lowest priority element is now out of place at the top of the queue, it is
     * allowed to sink until it reaches where it is supposed to be in priority.
     */
    public T remove()
    {
        T temp = getRoot();
        swapIndexes(0, --size);
        prio.remove(size);
        sink();

        return temp;
    }

    /**
     * Causes the index passed to rise up to its priority.
     * 
     * @param index The index to bubbleUp.
     */
    private void bubbleUp(int index)
    {
        // as long as you have a parent and you're of a higher priority than your parent
        while (hasParent(index) && prio.get(index).compareTo(prio.get(parentOf(index))) < 0)
        {
            swapIndexes(index, parentOf(index));
            index = parentOf(index);
        }
    }

    /**
     * Sinks the element at the highest priority until it reaches their actual priority.
     * 
     * @return the index that the element sunk to.
     */
    private void sink()
    {
        int index = 0;
        while (hasLeftChild(index))
        {
            int smallerChild = childToPromote(index);
            if (prio.get(index).compareTo(prio.get(smallerChild)) >= 0)
            {
                swapIndexes(index, smallerChild);
                index = smallerChild;
            }
            else
                break; // it's sunk as low as it needs to
        }
    }

    /**
     * Swaps the indexes in a priority queue.
     *
     * @param index the first index.
     * @param swap the index it's swapping with.
     */
    private void swapIndexes(int index, int swap)
    {
        T temp = (T) prio.get(index);
        prio.set(index, prio.get(swap));
        prio.set(swap, temp);
    }

    /**
     * Returns the index of a child element that is of a higher priority than the other child
     * element.
     * 
     * @param index The index that has child to test against each other.
     * @return The index of the winner.
     */
    private int childToPromote(int index)
    {
        if (!hasRightChild(index))
            return leftChildOf(index);
        else
            return (prio.get(leftChildOf(index)).compareTo(prio.get(rightChildOf(index)))) <= 0 ? leftChildOf(index)
                    : rightChildOf(index);
    }

    /**
     * Get the index of the left child of the index you passed to it. Does not guarantee that
     * the index exists in the list.
     *
     * @param index the index to check.
     * @return The index that the left child is located at.
     */
    private int leftChildOf(int index)
    {
        return (2 * index) + 1;
    }

    /**
     * Get the index of the right child of the index you passed to it. Does not guarantee that
     * the index exists in the list.
     *
     * @param index the index to check.
     * @return The index that the right child is located at.
     */
    private int rightChildOf(int index)
    {
        return (2 * index) + 2;
    }

    /**
     * Returns whether or not an element has a child to it's left. Elements are defined as
     * having a left child if the index equal to twice their index, plus 1, exists in the
     * priority queue.
     * 
     * @param index The index to check.
     * @return Whether or not the node has a left child.
     */
    private boolean hasLeftChild(int index)
    {
        return leftChildOf(index) < size;
    }

    /**
     * Returns whether or not an element has a child to it's right. Elements are defined as
     * having a left right if the index equal to twice their index, plus 2, exists in the
     * priority queue.
     * 
     * @param index The index to check.
     * @return Whether or not the node has a right child.
     */
    private boolean hasRightChild(int index)
    {
        return rightChildOf(index) < size;
    }

    /**
     * Get the index of the parent of the index you passed to it. Does not guarantee that the
     * index exists in the list.
     *
     * @param index the index to check.
     * @return The index that the parent is located at.
     */
    private int parentOf(int index)
    {
        return (index - 1) / 2;
    }

    /**
     * Checks to see if the priority queue is empty.
     *
     * @return Returns true if there is nothing in the queue, false otherwise.
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns how many objects are currently in the queue.
     *
     * @return Size of the queue.
     */
    public int size()
    {
        return size;
    }

    /**
     * Gives a reference to the first object in the queue.
     *
     * @return The element in the queue with the most priority.
     */
    private T getRoot()
    {
        return (T) prio.get(0);
    }

    private boolean hasParent(int index)
    {
        return index > 0; // only the root does not have a parent.
    }

    public String toString()
    {
        StringBuilder tempString = new StringBuilder();
        for (T element : prio)
        {
            tempString.append(element.toString() + ", ");
        }
        tempString.setLength(tempString.length() - 2); // cut off the last space and comma.
        return tempString.toString();
    }
}
