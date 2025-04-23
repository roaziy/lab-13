package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {
        // comment/uncomment these lines to test each class
        mQueue = new LinkedIntQueue();
        // mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {
        // This is an example unit test
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        IntQueue queue = new ArrayIntQueue();
        assertTrue("New queue should be empty", queue.isEmpty());

        queue.enqueue(10);
        assertFalse("Queue with elements should not be empty", queue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        IntQueue queue = new ArrayIntQueue();

        // According to the interface, peek() should return null for empty queue
        assertNull("Peek on empty queue should return null", queue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        IntQueue queue = new ArrayIntQueue();
        Integer testValue = 42;
        queue.enqueue(testValue);
        queue.enqueue(73);

        // Peek should return the first element without removing it
        assertEquals("Peek should return first element", testValue, queue.peek());
        assertEquals("Peek should not remove elements", testValue, queue.peek());
        assertEquals("Queue size should not change after peek", 2, queue.size());
    }

    @Test
    public void testEnqueue() {
        // This is an example unit test
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        IntQueue queue = new ArrayIntQueue();
        Integer first = 10;
        Integer second = 20;
        Integer third = 30;

        queue.enqueue(first);
        queue.enqueue(second);
        queue.enqueue(third);

        assertEquals("Queue should have 3 elements", 3, queue.size());
        assertEquals("First dequeue should return first element", first, queue.dequeue());
        assertEquals("Queue should have 2 elements after one dequeue", 2, queue.size());
        assertEquals("Second dequeue should return second element", second, queue.dequeue());
        assertEquals("Third dequeue should return third element", third, queue.dequeue());
        assertTrue("Queue should be empty after dequeueing all elements", queue.isEmpty());

        // Test dequeue on empty queue
        assertNull("Dequeue on empty queue should return null", queue.dequeue());
    }

    @Test
    public void testContent() throws IOException {
        // This is an example unit test
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testClear() {
        IntQueue queue = new ArrayIntQueue();

        // Add some elements
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals(3, queue.size());

        // Clear the queue
        queue.clear();

        // Verify queue is empty
        assertTrue("Queue should be empty after clear", queue.isEmpty());
        assertEquals("Queue size should be 0 after clear", 0, queue.size());
        assertNull("Peek should return null after clear", queue.peek());
        assertNull("Dequeue should return null after clear", queue.dequeue());

        // Verify we can still add elements after clearing
        assertTrue(queue.enqueue(40));
        assertEquals(Integer.valueOf(40), queue.peek());
    }

    @Test
    public void testEnsureCapacity() {
        IntQueue queue = new ArrayIntQueue();

        // Add more than the initial capacity (10) elements
        for (int i = 0; i < 15; i++) {
            assertTrue(queue.enqueue(i));
        }

        // Verify all elements are still in the queue in the right order
        assertEquals(15, queue.size());

        for (int i = 0; i < 15; i++) {
            assertEquals(Integer.valueOf(i), queue.dequeue());
        }

        assertTrue(queue.isEmpty());
    }

    @Test
    public void testWrappingQueue() {
        IntQueue queue = new ArrayIntQueue();

        // First fill and partially empty to create a wrap situation
        for (int i = 0; i < 7; i++) {
            queue.enqueue(i);
        }

        // Remove some elements to move the head
        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i), queue.dequeue());
        }

        // Add more elements to force wrap-around and capacity increase
        for (int i = 100; i < 115; i++) {
            queue.enqueue(i);
        }

        // Verify queue size
        assertEquals(17, queue.size());

        // Verify remaining original elements
        assertEquals(Integer.valueOf(5), queue.dequeue());
        assertEquals(Integer.valueOf(6), queue.dequeue());

        // Verify new elements
        for (int i = 100; i < 115; i++) {
            assertEquals(Integer.valueOf(i), queue.dequeue());
        }

        // Queue should be empty now
        assertTrue(queue.isEmpty());
    }
}
