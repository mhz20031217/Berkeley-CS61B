import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOffByOne() {
        for (int d = 2; d < 0x3fff; ++d) {
            for (char x = '\0', y = (char) (x + d); y < 0x3fff; ++x, ++y) {
                assertFalse(offByOne.equalChars(x, y));
                assertFalse(offByOne.equalChars(y, x));
            }
        }
        for (char x = '\0'; x < 0x3fff; ++x) {
            assertFalse(offByOne.equalChars(x, x));
        }
        for (char x = '\0', y = '\1'; y < 0x3fff; ++x, ++y) {
            assertTrue(offByOne.equalChars(x, y));
            assertTrue(offByOne.equalChars(y, x));
        }
    }
}
