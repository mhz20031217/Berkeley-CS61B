public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readLine();
        return in.readDouble();
    }
    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int n = in.readInt();
        Planet[] ret = new Planet[n];
        in.readDouble();
        for (int i = 0; i < n; ++i) {
            ret[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(),in.readDouble(),in.readString());
        }
        return ret;
    }
}
