import edu.princeton.cs.algs4.Queue;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item: q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param lt      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param eq   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param gt   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> lt, Queue<Item> eq, Queue<Item> gt) {
        while (!unsorted.isEmpty()) {
            Item item = unsorted.dequeue();
            int cmp = item.compareTo(pivot);
            if (cmp < 0) {
                lt.enqueue(item);
            } else if (cmp > 0) {
                gt.enqueue(item);
            } else {
                eq.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        if (items.size() <= 1) {
            return items;
        }
        Item pivot = getRandomItem(items);
        Queue<Item> lt, eq, gt;
        lt = new Queue<>();
        eq = new Queue<>();
        gt = new Queue<>();
        partition(items, pivot, lt, eq, gt);
        lt = quickSort(lt);
        gt = quickSort(gt);
        items = catenate(lt, catenate(eq, gt));
        return items;
    }

    @Test
    public void testQuickSort1() {
        Integer[] orin = {9, 1, 3, 5, 6, 8, 7, 4, 2};
        Integer[] ans = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Queue<Integer> queue = new Queue<>();
        for (Integer i : orin) {
            queue.enqueue(i);
        }
        Queue<Integer> sorted = quickSort(queue);
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
    public void testQuickSort2() {
        Integer[] orin = {4, 6, 1, 4, 7, 9, 1, 6, 0, 1};
        Integer[] ans = {0, 1, 1, 1, 4, 4, 6, 6, 7, 9};
        Queue<Integer> queue = new Queue<>();
        for (Integer i : orin) {
            queue.enqueue(i);
        }
        Queue<Integer> sorted = quickSort(queue);
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
