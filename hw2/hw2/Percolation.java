package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int[] DX = {0, 0, 1, -1},
            DY = {1, -1, 0, 0};
    private final WeightedQuickUnionUF ds, dsS, dsE;
    private final boolean[][] map;
    private final int scale;
    private int count;
    private final int st, ed;
    private boolean percolates;

    private int index(int x, int y) {
        return x * scale + y;
    }

    private boolean inRange(int x, int y) {
        return x >= 0 && x < scale && y >= 0 && y < scale;
    }

    public Percolation(int N) {
        percolates = false;
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        scale = N;
        map = new boolean[N][N];
        ds = new WeightedQuickUnionUF(N * N + 2);
        dsS = new WeightedQuickUnionUF(N * N + 2);
        dsE = new WeightedQuickUnionUF(N * N + 2);
        st = N * N;
        ed = st + 1;
    }

    public void open(int row, int col) {
        if (!inRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        if (map[row][col]) {
            return;
        }
        ++count;
        map[row][col] = true;
        int p = index(row, col);
        for (int k = 0; k < 4; ++k) {
            int nx = row + DX[k], ny = col + DY[k];
            if (!inRange(nx, ny) || !isOpen(nx, ny)) {
                continue;
            }
            int np = index(nx, ny);
            ds.union(p, np);
            dsS.union(p, np);
            dsE.union(p, np);
        }
        if (row == 0) {
            ds.union(p, st);
            dsS.union(p, st);
        }
        if (row == scale - 1) {
            ds.union(p, ed);
            dsE.union(p, ed);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!inRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return map[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!inRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int p = index(row, col);
        return dsS.connected(p, st);
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return ds.connected(st, ed);
    }

    public static void main(String[] args) {
    }
}
