import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private int width, height;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        Color l, r, u, d;
        l = picture.get(Math.floorMod(x - 1, width), y);
        r = picture.get(Math.floorMod(x + 1, width), y);
        u = picture.get(x, Math.floorMod(y - 1, height));
        d = picture.get(x, Math.floorMod(y + 1, height));
        int rx, ry, gx, gy, bx, by;
        rx = r.getRed() - l.getRed();
        ry = d.getRed() - u.getRed();
        gx = r.getGreen() - l.getGreen();
        gy = d.getGreen() - u.getGreen();
        bx = r.getBlue() - l.getBlue();
        by = d.getBlue() - u.getBlue();
        return rx * rx + ry * ry + gx * gx + gy * gy + bx * bx + by * by;
    }

    public int[] findHorizontalSeam() {
        Picture invPicture = invPicture();
        SeamCarver invSC = new SeamCarver(invPicture);
        return invSC.findVerticalSeam();
    }

    public int[] findVerticalSeam() {
        if (width < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (width == 1) {
            return new int[height];
        }
        double[][] energyMap = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energyMap[i][j] = energy(i, j);
            }
        }

        double[][] dp = new double[width][height];
        int[][] prev = new int[width][height];
        for (int i = 0; i < width; i++) {
            dp[i][0] = energyMap[i][0];
            prev[i][0] = 0;
        }
        for (int j = 1; j < height; j++) {
            int i = 0;
            if (dp[i][j - 1] < dp[i + 1][j - 1]) {
                prev[i][j] = 0;
                dp[i][j] = dp[i][j - 1] + energyMap[i][j];
            } else {
                prev[i][j] = 1;
                dp[i][j] = dp[i + 1][j - 1] + energyMap[i][j];
            }
            for (i = 1; i < width - 1; i++) {
                if (dp[i - 1][j - 1] <= dp[i][j - 1] && dp[i - 1][j - 1] <= dp[i + 1][j - 1]) {
                    prev[i][j] = -1;
                    dp[i][j] = dp[i - 1][j - 1] + energyMap[i][j];
                } else if (dp[i][j - 1] <= dp[i - 1][j - 1] && dp[i][j - 1] <= dp[i + 1][j - 1]) {
                    prev[i][j] = 0;
                    dp[i][j] = dp[i][j - 1] + energyMap[i][j];
                } else {
                    prev[i][j] = 1;
                    dp[i][j] = dp[i + 1][j - 1] + energyMap[i][j];
                }
            }
            i = width - 1;
            if (dp[i][j - 1] < dp[i - 1][j - 1]) {
                prev[i][j] = 0;
                dp[i][j] = dp[i][j - 1] + energyMap[i][j];
            } else {
                prev[i][j] = -1;
                dp[i][j] = dp[i - 1][j - 1] + energyMap[i][j];
            }
        }
        double bestn = 1E9;
        int bestp = 0;
        for (int i = 0; i < width; i++) {
            if (dp[i][height - 1] < bestn) {
                bestn = dp[i][height - 1];
                bestp = i;
            }
        }
        int i = bestp, j = height - 1;
        int[] ret = new int[height];
        while (j > 0) {
            ret[j] = i;
            i += prev[i][j];
            --j;
        }
        ret[j] = i;
        return ret;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (height < 1 || seam.length != width) {
            throw new IndexOutOfBoundsException();
        }
        int[] hSeam = findHorizontalSeam();
        Picture nPicture = new Picture(width, height - 1);
        int delta;
        for (int i = 0; i < width; i++) {
            delta = 0;
            for (int j = 0; j < height; j++) {
                if (j == hSeam[i]) {
                    delta = -1;
                    continue;
                } else {
                    nPicture.set(i, j + delta, picture.get(i, j));
                }
            }
        }
        picture = nPicture;
        height--;
    }

    private Picture invPicture() {
        Picture invPicture = new Picture(height, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                invPicture.set(j, i, picture.get(i, j));
            }
        }
        return invPicture;
    }

    public void removeVerticalSeam(int[] seam) {
        if (width < 1 || seam.length != height) {
            throw new IndexOutOfBoundsException();
        }
        Picture invPicture = invPicture();
        SeamCarver invSC = new SeamCarver(invPicture);
        invSC.removeHorizontalSeam(invSC.findHorizontalSeam());
        picture = invSC.invPicture();
        width--;
    }
}
