public class Planet {
    private final double G = 6.67e-11;
    public double xxPos, yyPos;
    public double xxVel, yyVel;
    public double mass;
    public String imgFileName;
    public Planet(double xP, double yP, double xV, double yV, double m,
                  String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    public double calcDistance(Planet p) {
        return Math.sqrt((xxPos - p.xxPos) * (xxPos - p.xxPos) +
                         (yyPos - p.yyPos) * (yyPos - p.yyPos));
    }
    public double calcForceExertedBy(Planet p) {
        double n, m;
        n = G * mass * p.mass;
        double r = calcDistance(p);
        m = r * r;
        return n / m;
    }
    public double calcForceExertedByX(Planet p) {
        if (xxPos == p.xxPos)
            return 0;
        double f = calcForceExertedBy(p);
        double r = calcDistance(p);
        return f * (p.xxPos - xxPos) / r;
    }
    public double calcForceExertedByY(Planet p) {
        if (yyPos == p.yyPos)
            return 0;
        double f = calcForceExertedBy(p);
        double r = calcDistance(p);
        return f * (p.yyPos - yyPos) / r;
    }
    public double calcNetForceExertedByX(Planet[] lst) {
        double ret = 0;
        for (int i = 0; i < lst.length; ++i) {
            if (lst[i].equals(this))
                continue;
            ret += calcForceExertedByX(lst[i]);
        }
        return ret;
    }
    public double calcNetForceExertedByY(Planet[] lst) {
        double ret = 0;
        for (int i = 0; i < lst.length; ++i) {
            if (lst[i].equals(this))
                continue;
            ret += calcForceExertedByY(lst[i]);
        }
        return ret;
    }
}
