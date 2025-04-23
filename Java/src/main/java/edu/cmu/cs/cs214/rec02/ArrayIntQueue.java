package edu.cmu.cs.cs214.rec02;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A resizable-array implementation of the {@link IntQueue} interface. The head
 * of the queue starts out at the head of the array, allowing the queue to grow
 * and
 * shrink in constant time.
 *
 * @author Alex Lockwood
 * @author Ye Lu
 */
public class ArrayIntQueue implements IntQueue {

    private static final Logger logger = LogManager.getLogger(ArrayIntQueue.class);

    /**
     * An array holding this queue's data
     */
    private int[] elementData;

    /**
     * Index of the next dequeue-able value
     */
    private int head;

    /**
     * Current size of queue
     */
    private int size;

    /**
     * The initial size for new instances of ArrayQueue
     */
    private static final int INITIAL_SIZE = 10;

    /**
     * Constructs an empty queue with an initial capacity of ten.
     */
    public ArrayIntQueue() {
        elementData = new int[INITIAL_SIZE];
        head = 0;
        size = 0;
        logger.debug("Created new ArrayIntQueue with initial capacity {}", INITIAL_SIZE);
    }

    /** {@inheritDoc} */
    public void clear() {
        logger.debug("Clearing queue with {} elements", size);
        size = 0;
        head = 0;
        logger.info("Queue cleared");
    }

    /** {@inheritDoc} */
    public Integer dequeue() {
        if (isEmpty()) {
            logger.debug("Attempted to dequeue from empty queue");
            return null;
        }
        Integer value = elementData[head];
        head = (head + 1) % elementData.length;
        size--;
        logger.debug("Dequeued value: {}, new size: {}, head position: {}", value, size, head);
        return value;
    }

    /** {@inheritDoc} */
    public boolean enqueue(Integer value) {
        logger.debug("Enqueueing value: {}", value);
        ensureCapacity();
        int tail = (head + size) % elementData.length;
        elementData[tail] = value;
        size++;
        logger.debug("Enqueued value: {}, new size: {}, tail position: {}", value, size, tail);
        return true;
    }

    /** {@inheritDoc} */
    public boolean isEmpty() {
        boolean empty = size == 0;
        logger.trace("Queue isEmpty check returned: {}", empty);
        return empty;
    }

    /** {@inheritDoc} */
    public Integer peek() {
        if (isEmpty()) {
            logger.debug("Attempted to peek at empty queue");
            return null;
        }
        Integer value = elementData[head];
        logger.debug("Peek returned value: {}", value);
        return value;
    }

    /** {@inheritDoc} */
    public int size() {
        logger.trace("Queue size: {}", size);
        return size;
    }

    /**
     * Increases the capacity of this <tt>ArrayIntQueue</tt> instance, if
     * necessary, to ensure that it can hold at least size + 1 elements.
     */
    private void ensureCapacity() {
        // Only resize when array is full
        if (size == elementData.length) {
            int oldCapacity = elementData.length;
            int newCapacity = 2 * oldCapacity + 1;
            logger.info("Resizing queue from capacity {} to {}", oldCapacity, newCapacity);

            int[] newData = new int[newCapacity];

            // Copy all elements to the new array
            int i = 0;
            int j = head;

            // Copy elements one by one from old array to new array
            while (i < size) {
                newData[i++] = elementData[j];
                j = (j + 1) % oldCapacity;
            }

            logger.debug("Copied {} elements to new array, head reset from {} to 0", size, head);
            elementData = newData;
            head = 0;
        }
    }
}