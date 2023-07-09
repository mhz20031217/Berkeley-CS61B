/**
 * Body Emulator
 * 
 * @author Caterpillar
 * @version 1.0.0
 */
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
            ret[i] =
                new Planet(in.readDouble(), in.readDouble(), in.readDouble(),
                           in.readDouble(), in.readDouble(), in.readString());
        }
        return ret;
    }
    public static void main(String[] args) {
        assert (args.length == 3);
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];

        Planet[] planets = readPlanets(fileName);
        int n = planets.length;

        double radius = readRadius(fileName);
        StdDraw.clear();
        StdDraw.setScale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        for (double t = 0; t <= T; t += dt) {

            StdDraw.pause(10);
            double[] xForces = new double[n];
            double[] yForces = new double[n];

            for (int i = 0; i < n; ++i) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < n; ++i) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (int i = 0; i < n; ++i) {
                planets[i].draw();
            }

            StdDraw.show();
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                          planets[i].yyVel, planets[i].mass,
                          planets[i].imgFileName);
        }
    }
}
