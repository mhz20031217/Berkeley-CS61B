public class OffByN implements CharacterComparator {
    private int diff;

    public OffByN(int diff) {
        this.diff = diff;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == diff;
    }
}
