public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> dq = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            dq.addLast(word.charAt(i));
        }
        return dq;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> dq = wordToDeque(word);
        while (dq.size() >= 2) {
            char a = dq.removeFirst(), b = dq.removeLast();
            if (a != b) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> dq = wordToDeque(word);
        while (dq.size() >= 2) {
            char a = dq.removeFirst(), b = dq.removeLast();
            if (!cc.equalChars(a, b)) {
                return false;
            }
        }
        return true;
    }
}
