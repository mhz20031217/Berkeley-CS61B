package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period, state;
    private final double ratio;
    public AcceleratingSawToothGenerator(int period, double ratio) {
        this.period = period;
        this.ratio = ratio;
        this.state = 0;
    }

    @Override
    public double next() {
        double ret = state * (2.0 / period) - 1;
        if (state == period - 1) {
            period *= ratio;
            state = 0;
        } else {
            state++;
        }
        return ret;
    }
}
