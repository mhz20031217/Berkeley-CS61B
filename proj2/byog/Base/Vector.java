package byog.Base;

public class Vector {
    public int x, y;

    public double length() {
        return Math.sqrt(x*x + y*y);
    }
}
