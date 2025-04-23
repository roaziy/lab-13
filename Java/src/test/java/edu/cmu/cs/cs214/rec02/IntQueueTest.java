package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * TODO:
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some
 * example test cases.
 * Write your own unit tests to test against IntQueue interface with
 * specification testing method
 * using mQueue = new LinkedIntQueue();
 * 
 * 2.
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new
 * ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the
 * {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with
 * structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {

    private static final Logger logger = LogManager.getLogger(IntQueueTest.class);

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        logger.info("Setting up test with LinkedIntQueue");
        mQueue = new LinkedIntQueue();
        // mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
        logger.debug("Test list initialized with values: {}", testList);
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        logger.info("Running testIsEmpty");
        assertTrue(mQueue.isEmpty());
        logger.debug("Queue is empty as expected");
    }

    @Test
    public void testNotEmpty() {
        logger.info("Running testNotEmpty");
        IntQueue queue = new ArrayIntQueue();
        assertTrue("New queue should be empty", queue.isEmpty());
        logger.debug("New queue is empty as expected");

        queue.enqueue(10);
        assertFalse("Queue with elements should not be empty", queue.isEmpty());
        logger.debug("Queue is not empty after enqueue as expected");
    }

    @Test
    public void testPeekEmptyQueue() {
        logger.info("Running testPeekEmptyQueue");
        IntQueue queue = new ArrayIntQueue();

        // According to the interface, peek() should return null for empty queue
        assertNull("Peek on empty queue should return null", queue.peek());
        logger.debug("Peek on empty queue returned null as expected");
    }

    @Test
    public void testPeekNoEmptyQueue() {
        logger.info("Running testPeekNoEmptyQueue");
        IntQueue queue = new ArrayIntQueue();
        Integer testValue = 42;
        queue.enqueue(testValue);
        queue.enqueue(73);
        logger.debug("Enqueued values: 42, 73");

        // Peek should return the first element without removing it
        assertEquals("Peek should return first element", testValue, queue.peek());
        assertEquals("Peek should not remove elements", testValue, queue.peek());
        assertEquals("Queue size should not change after peek", 2, queue.size());
        logger.debug("Peek verified to return first element (42) without removal");
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        logger.info("Running testEnqueue");
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            logger.debug("Enqueued value: {}", testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
        logger.debug("All values enqueued successfully, queue size: {}", mQueue.size());
    }

    @Test
    public void testDequeue() {
        logger.info("Running testDequeue");
        IntQueue queue = new ArrayIntQueue();
        Integer first = 10;
        Integer second = 20;
        Integer third = 30;

        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);
        logger.debug("Enqueued values: 10, 20, 30");

        assertEquals("Queue should have 3 elements", 3, queue.size());
        assertEquals("First dequeue should return first element", first, queue.dequeue());
        logger.debug("Dequeued first value: {}", first);
        assertEquals("Queue should have 2 elements after one dequeue", 2, queue.size());
        assertEquals("Second dequeue should return second element", second, queue.dequeue());
        logger.debug("Dequeued second value: {}", second);
        assertEquals("Third dequeue should return third element", third, queue.dequeue());
        logger.debug("Dequeued third value: {}", third);
        assertTrue("Queue should be empty after dequeueing all elements", queue.isEmpty());

        // Test dequeue on empty queue
        assertNull("Dequeue on empty queue should return null", queue.dequeue());
        logger.debug("Dequeue on empty queue returned null as expected");
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        logger.info("Running testContent");
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                logger.debug("Enqueuing from file: {}", input);
                mQueue.enqueue(input);
            }
            logger.debug("Enqueued {} elements from file", correctResult.size());

            for (Integer result : correctResult) {
                Integer dequeued = mQueue.dequeue();
                logger.debug("Dequeued: {}, Expected: {}", dequeued, result);
                assertEquals(dequeued, result);
            }
            logger.debug("All file content values verified");
        }
    }

    @Test
    public void testClear() {
        logger.info("Running testClear");
        IntQueue queue = new ArrayIntQueue();

        // Add some elements
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(3, queue.size());
        logger.debug("Enqueued 3 elements before clear");

        // Clear the queue
        queue.clear();
        logger.debug("Queue cleared");

        // Verify queue is empty
        assertTrue("Queue should be empty after clear", queue.isEmpty());
        assertEquals("Queue size should be 0 after clear", 0, queue.size());
        assertNull("Peek should return null after clear", queue.peek());
        assertNull("Dequeue should return null after clear", queue.dequeue());
        logger.debug("Verified queue is empty after clear");

        // Verify we can still add elements after clearing
        assertTrue(queue.enqueue(40));
        assertEquals(Integer.valueOf(40), queue.peek());
        logger.debug("Successfully added new element after clear");
    }

    @Test
    public void testEnsureCapacity() {
        logger.info("Running testEnsureCapacity");
        IntQueue queue = new ArrayIntQueue();

        // Add more than the initial capacity (10) elements
        for (int i = 0; i < 15; i++) {
            assertTrue(queue.enqueue(i));
            logger.debug("Enqueued value: {}", i);
        }

        // Verify all elements are still in the queue in the right order
        assertEquals(15, queue.size());
        logger.debug("Queue size after enqueuing 15 elements: {}", queue.size());

        for (int i = 0; i < 15; i++) {
            Integer value = queue.dequeue();
            logger.debug("Dequeued value: {}, Expected: {}", value, i);
            assertEquals(Integer.valueOf(i), value);
        }

        assertTrue(queue.isEmpty());
        logger.debug("Queue is empty after dequeuing all elements");
    }

    @Test
    public void testWrappingQueue() {
        logger.info("Running testWrappingQueue");
        IntQueue queue = new ArrayIntQueue();

        // First fill and partially empty to create a wrap situation
        for (int i = 0; i < 7; i++) {
            queue.enqueue(i);
            logger.debug("Initial enqueue: {}", i);
        }

        // Remove some elements to move the head
        for (int i = 0; i < 5; i++) {
            Integer value = queue.dequeue();
            logger.debug("Initial dequeue: {}, Expected: {}", value, i);
            assertEquals(Integer.valueOf(i), value);
        }

        // Add more elements to force wrap-around and capacity increase
        for (int i = 100; i < 115; i++) {
            queue.enqueue(i);
            logger.debug("Secondary enqueue: {}", i);
        }

        // Verify queue size
        assertEquals(17, queue.size());
        logger.debug("Queue size after operations: {}", queue.size());

        // Verify remaining original elements
        Integer value = queue.dequeue();
        logger.debug("Dequeued remaining original element: {}, Expected: 5", value);
        assertEquals(Integer.valueOf(5), value);

        value = queue.dequeue();
        logger.debug("Dequeued remaining original element: {}, Expected: 6", value);
        assertEquals(Integer.valueOf(6), value);

        // Verify new elements
        for (int i = 100; i < 115; i++) {
            value = queue.dequeue();
            logger.debug("Dequeued wrapped element: {}, Expected: {}", value, i);
            assertEquals(Integer.valueOf(i), value);
        }

        // Queue should be empty now
        assertTrue(queue.isEmpty());
        logger.debug("Queue is empty after all operations");
    }
}
