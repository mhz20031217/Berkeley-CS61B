package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);

        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());

        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        assertEquals(arb.fillCount(), 3);
        assertEquals(arb.capacity(), 10);

        for (int i = 4; i <= 10; i++) {
            arb.enqueue(i);
        }

        assertEquals(arb.fillCount(), 10);
        assertTrue(arb.isFull());
        assertFalse(arb.isEmpty());

        int tmp = arb.dequeue();
        assertEquals(tmp, 1);
        assertEquals((int) arb.peek(), 2);

        for (int i = 0; i < 9; i++) {
            arb.dequeue();
        }

        assertEquals(arb.fillCount(), 0);
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
