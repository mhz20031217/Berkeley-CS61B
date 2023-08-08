package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {

    private final int period;
    private int t;

    public SawToothGenerator(int period) {
        this.period = period;
        this.t = 0;
    }


    @Override
    public double next() {
        double ret;
        ret = (t % period) * (2.0 / period) - 1;
        ++t;
        return ret;
    }
}
