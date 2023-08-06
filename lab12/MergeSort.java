import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        return null;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        return null;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        return items;
    }

    @Test
    public void testMergeSort1() {
        Integer[] orin = {9, 1, 3, 5, 6, 8, 7, 4, 2};
        Integer[] ans = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Queue<Integer> queue = new Queue<>();
        for (Integer i : orin) {
            queue.enqueue(i);
        }
        Queue<Integer> sorted = mergeSort(queue);
        for (int i = 0; i < 9; i++) {
            orin[i] = sorted.dequeue();
        }
        boolean flag = true;
        for (int i = 0; i < 9; i++) {
            if (!orin[i].equals(ans[i])) {
                flag = false;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    public void testMergeSort2() {
        Integer[] orin = {4, 6, 1, 4, 7, 9, 1, 6, 0, 1};
        Integer[] ans = {0, 1, 1, 1, 4, 4, 6, 6, 7, 9};
        Queue<Integer> queue = new Queue<>();
        for (Integer i : orin) {
            queue.enqueue(i);
        }
        Queue<Integer> sorted = mergeSort(queue);
        for (int i = 0; i < 10; i++) {
            orin[i] = sorted.dequeue();
        }
        boolean flag = true;
        for (int i = 0; i < 10; i++) {
            if (!orin[i].equals(ans[i])) {
                flag = false;
                break;
            }
        }
        assertTrue(flag);
    }
}
