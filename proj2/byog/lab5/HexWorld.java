package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.*;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private final int WIDTH, HEIGHT, SEED;
    private final TETile[][] world;
    private final TERenderer render = new TERenderer();
    private final Random random;
    private final TETile[] tiles = {
            Tileset.FLOOR, Tileset.WALL, Tileset.GRASS, Tileset.FLOWER,
            Tileset.TREE, Tileset.MOUNTAIN, Tileset.SAND, Tileset.WATER
    };

    HexWorld(int WIDTH, int HEIGHT, int SEED) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.SEED = SEED;
        world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        random = new Random(SEED);
    }

    /**
     * Whether the given coordinate (X, Y) is in the range of world.
     * @param x coordinate x
     * @param y coordinate y
     * @return true - in, false - out
     */
    private boolean inRange(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    /**
     * Add a hexagon on WORLD at (X, Y) with given SIZE.
     * @param x the x coordinate of the top-left corner of the hexagon
     * @param y the y coordinate of the top-left corner of the hexagon
     * @param size the size of the hexagon
     * @return 0 - success, -1 - some part of the hexagon is out of range
     */
    private int addHexagon(int x, int y, int size, TETile tile) {
        int nx, ny;
        int status = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = -i; j < size + i; ++j) {
                nx = x + i; ny = y + j;
                if (inRange(ny, nx)) {
                    world[ny][nx] = tile;
                } else {
                    status = -1;
                }
            }
        }
        for (int i = size; i < 2*size; ++i) {
            int diff = 2*size - 1 - i;
            for (int j = - diff; j < size + diff; ++j) {
                nx = x + i; ny = y + j;
                if (inRange(ny, nx)) {
                    world[ny][nx] = tile;
                } else {
                    status = -1;
                }
            }
        }
        return status;
    }

    private int guiInitialize() {
        render.initialize(WIDTH, HEIGHT);
        return 0;
    }

    private int guiRefresh() {
        render.renderFrame(world);
        return 0;
    }

    private TETile randomTile() {
        int o = random.nextInt(tiles.length);
        return tiles[o];
    }

    public static void main(String[] args) {
        HexWorld world = new HexWorld(80, 24, 233);

        int i, j, size;
        for (size = 2, i = size, j = size; size < 7; ++size, i = size, j += 3*size + 1) {
            world.addHexagon(i, j, size, world.randomTile());
        }

        world.guiInitialize();
        world.guiRefresh();
    }
    
}
