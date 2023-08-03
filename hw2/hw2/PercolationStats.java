package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.*;

public class PercolationStats {
    private final Percolation[] array;
    private final double[] res;
    int N;

    private int getX(int index) {
        return index / N;
    }

    private int getY(int index) {
        return index % N;
    }

    private void carryOutExperiment(int i) {
        Percolation box = array[i];
        Integer[] order = new Integer[N * N];
        for (int j = 0; j < N * N; j++) {
            order[j] = j;
        }
        StdRandom.shuffle(order);
        int count = 0;
        while (!box.percolates()) {
            box.open(getX(order[count]), getY(order[count]));
            ++count;
        }
        res[i] = (double) count / (N * N);
    }

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        array = new Percolation[N];
        res = new double[N];
        for (int i = 0; i < N; i++) {
            array[i] = pf.make(N);
            carryOutExperiment(i);
        }
    }

    public double mean() {
        return StdStats.mean(res);
    }

    public double stddev() {
        return StdStats.stddev(res);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(N);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(N);
    }

}
