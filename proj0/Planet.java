/**
 * Represents a Planet at (XXPOS, YYPOS) with the velocity of (XXVEL, YYVEL), mass of MASS and a image whose filename is IMGFILENAME.
 * 
 * @author Caterpillar
 * @version 1.0.0
 */
public class Planet {
    private final double G = 6.67e-11;
    public double xxPos, yyPos;
    public double xxVel, yyVel;
    public double mass;
    public String imgFileName;
	/**
	 * Constructor of Planet from scratch.
	 * @param xP X-coordinate
	 * @param yP Y-coordinate
	 * @param xV X-velocity
	 * @param yV Y-velocity
	 * @param m Mass
	 * @param img The filename of the image of the planet.
	 */
    public Planet(double xP, double yP, double xV, double yV, double m,
                  String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
	/**
	 * Planet constructor from a reference Planet.
	 * @param p the reference Planet
	 */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
	/**
	 * Calculate the distance between this planet and Planet p.
	 * @param p
	 * @return distance
	 */
    public double calcDistance(Planet p) {
        return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) +
                         (yyPos - p.yyPos) * (yyPos - p.yyPos));
    }
	/**
	 * Calc the force exerted on this planet by the given planet.
	 * @param p
	 * @return the force exerted by P on this planet.
	 */
    public double calcForceExertedBy(Planet p) {
        double n, m;
        n = G * mass * p.mass;
        double r = calcDistance(p);
        m = r * r;
        return n / m;
    }
	/**
	 * Calc the force exerted by P in the X direction.
	 * @param p
	 * @return the force exerted by P on this planet in the X direction.
	 */
    public double calcForceExertedByX(Planet p) {
        if (xxPos == p.xxPos)
            return 0;
        double f = calcForceExertedBy(p);
        double r = calcDistance(p);
        return f * (p.xxPos - xxPos) / r;
    }
	/**
	 * @see calcForceExertedByX
	 */
    public double calcForceExertedByY(Planet p) {
        if (yyPos == p.yyPos)
            return 0;
        double f = calcForceExertedBy(p);
        double r = calcDistance(p);
        return f * (p.yyPos - yyPos) / r;
    }
	/**
	 * 
	 * @param lst
	 * @return
	 */
    public double calcNetForceExertedByX(Planet[] lst) {
        double ret = 0;
        for (int i = 0; i < lst.length; ++i) {
            if (lst[i].equals(this))
                continue;
            ret += calcForceExertedByX(lst[i]);
        }
        return ret;
    }
	/**
	 * @see calcNetForceExertedByX
	 */
    public double calcNetForceExertedByY(Planet[] lst) {
        double ret = 0;
        for (int i = 0; i < lst.length; ++i) {
            if (lst[i].equals(this))
                continue;
            ret += calcForceExertedByY(lst[i]);
        }
        return ret;
    }
	public void update(double t, double fx, double fy) {
		xxVel += fx / mass * t;
		yyVel += fy / mass * t;
		xxPos += xxVel * t;
		yyPos += yyVel * t;
	}
}
