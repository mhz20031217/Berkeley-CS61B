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
}
