import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

public class TestArrayDequeGold {

    static StudentArrayDeque<Integer> sol = new StudentArrayDeque<>();
    static ArrayDequeSolution<Integer> std = new ArrayDequeSolution<>();
    String msg = "";

    boolean booleanDice() {
        return StdRandom.uniform() >= 0.5;
    }
    int intDice() {
        return StdRandom.uniform(-200000000,200000000);
    }

    void addBegin(Integer item) {
        msg += "addFirst("+item+")\n";
        sol.addFirst(item);
        std.addFirst(item);
    }

    void addEnd(Integer item) {
        msg += "addLast("+item+")\n";
        sol.addLast(item);
        std.addLast(item);
    }

    boolean testBegin() {
        msg += "removeFirst()\n";
        Integer a = sol.removeFirst();
        Integer b = std.removeFirst();
        assertEquals(msg, b, a);
        return a != null && a.equals(b);
    }

    boolean testEnd() {
        msg += "removeEnd()\n";
        Integer a = sol.removeLast();
        Integer b = std.removeLast();
        assertEquals(msg, b, a);
        return a != null && a.equals(b);
    }

    @Test
    public void test() {
        int size = 0;

        while (true) {
            boolean begin_or_end = booleanDice();
            boolean push_or_pop = booleanDice();
            Integer item = intDice();
            if (size == 0) {
                if (begin_or_end) {
                    addBegin(item);
                } else {
                    addEnd(item);
                }
                ++size;
            } else {
                if (push_or_pop) {
                    if (begin_or_end) {
                        addBegin(item);
                    } else {
                        addEnd(item);
                    }
                    ++size;
                } else {
                    if (begin_or_end) {
                        if (!testBegin()) {
                            break;
                        }
                    } else {
                        if (!testEnd()) {
                            break;
                        }
                    }
                    --size;
                }
            }
        }

        System.out.println("Success");
    }

}
