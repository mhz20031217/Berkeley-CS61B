import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static OffByOne offbyone = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("abcdedcba"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome(" "));
        assertFalse(palindrome.isPalindrome("123rty321"));

        assertTrue(palindrome.isPalindrome("&%", offbyone));
        assertTrue(palindrome.isPalindrome("aceghfdb", offbyone));
        assertTrue(palindrome.isPalindrome("a", offbyone));
        assertFalse(palindrome.isPalindrome("aa", offbyone));
    }

    @Test
    public void testOffByOne() {
        for (int d = 2; d < 0x3fff; ++d) {
            for (char x = '\0', y = (char) (x + d); y < 0x3fff; ++x, ++y) {
                assertFalse(offbyone.equalChars(x, y));
                assertFalse(offbyone.equalChars(y, x));
            }
        }
        for (char x = '\0'; x < 0x3fff; ++x) {
            assertFalse(offbyone.equalChars(x, x));
        }
        for (char x = '\0', y = '\1'; y < 0x3fff; ++x, ++y) {
            assertTrue(offbyone.equalChars(x, y));
            assertTrue(offbyone.equalChars(y, x));
        }
    }

    @Test
    public void testOffByN() {
        for (int d = 0; d < 256; ++d) {
            OffByN offbyn = new OffByN(d);
            for (int diff = 0; diff < 256; ++diff) {
                for (char x = '\0', y = (char) (x + diff); y < 256; ++x, ++y) {
                    if (diff == d) {
                        assertTrue(offbyn.equalChars(x, y));
                        assertTrue(offbyn.equalChars(y, x));
                    } else {
                        assertFalse(offbyn.equalChars(x, y));
                        assertFalse(offbyn.equalChars(y, x));
                    }

                }
            }
        }
    }
}
