package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] res;
    private final int T, N;

    private int getX(int index) {
        return index / N;
    }

    private int getY(int index) {
        return index % N;
    }


    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        this.N = N;
        res = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation box = pf.make(N);
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
    }

    public double mean() {
        return StdStats.mean(res);
    }

    public double stddev() {
        return StdStats.stddev(res);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(10, 10, new PercolationFactory());
        System.out.println(ps.mean());
    }
}
