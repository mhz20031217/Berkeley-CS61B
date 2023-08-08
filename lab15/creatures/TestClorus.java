package creatures;

import huglife.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus a = new Clorus(1.0);

        assertEquals("Wrong name.", "clorus", a.name());

        assertTrue("Bad constructor or energy().", 1.0 == a.energy());
        a.move();
        assertTrue("Wrong moving energy cost.", 0.97 == a.energy());
        a.move();
        assertTrue("Wrong moving energy cost.", 0.94 == a.energy());
        a.stay();
        assertTrue("Wrong moving energy cost.", Math.abs(a.energy() - 0.93) < 1E-6);

    }

    @Test
    public void testReplicate() {
        Clorus a = new Clorus(2);
        Clorus b = a.replicate();
        assertNotSame(a, b);
        assertTrue(1.0 == a.energy());
        assertTrue(1.0 == b.energy());
    }

    @Test
    public void testChoose() {
        Clorus c = new Clorus(0.5);
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual, expected;
        actual = c.chooseAction(surrounded);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(surrounded);
        assertEquals(Action.ActionType.MOVE, actual.type);

        surrounded.put(Direction.TOP, new Plip());
        Plip food = new Plip(1);
        surrounded.put(Direction.BOTTOM, food);
        surrounded.put(Direction.LEFT, new Plip());
        surrounded.put(Direction.RIGHT, new Plip());

        actual = c.chooseAction(surrounded);

        assertEquals(Action.ActionType.STAY, actual.type);

        surrounded.put(Direction.TOP, new Empty());
        actual = c.chooseAction(surrounded);
        assertEquals(Action.ActionType.ATTACK, actual.type);
        assertNotEquals(Direction.TOP, actual.dir);

        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());
        actual = c.chooseAction(surrounded);
        assertEquals(Action.ActionType.ATTACK, actual.type);
        assertEquals(Direction.BOTTOM, actual.dir);

        c.attack(food);
        assertTrue(Math.abs(c.energy() - 1.5) < 1E-6);
        surrounded.put(Direction.BOTTOM, new Empty());
        actual = c.chooseAction(surrounded);
        assertEquals(Action.ActionType.REPLICATE, actual.type);
    }
}
