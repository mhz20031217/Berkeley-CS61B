package byog.Base;

public class Position implements Comparable<Position>{
    public int x, y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Position() {}

    /**
     * @param o
     * @return
     */
    @Override
    public int compareTo(Position o) {
        if (this.x != o.x) {
            return Integer.compare(this.x, o.x);
        }
        if (this.y != o.y) {
            return Integer.compare(this.y, o.y);
        }
        return 0;
    }
}
