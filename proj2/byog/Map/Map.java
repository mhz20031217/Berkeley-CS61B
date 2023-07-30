package byog.Map;

import byog.Base.Position;
import byog.Block.Block;
import byog.Block.BlockSet;
import byog.Creature.Creature;
import byog.TileEngine.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Map {
    final int TYPE, WIDTH, HEIGHT, SEED;
    final Block[][] world;
    final ArrayList<Creature> creatures;
    final TETile[][] buffer;
    private final TERenderer renderer;
    final Random random;
    private final MapGen generator;

    private void updateBuffer(Set<Position> set) {
        for (Position pos: set) {
            buffer[pos.x][pos.y] = world[pos.x][pos.y].getTile();
        }
    }

    private void updateBuffer(Position s, Position e) {
        for (int i = s.x; i < e.x; ++i) {
            for (int j = s.y; j < e.y; ++j) {
                buffer[i][j] = world[i][j].getTile();
            }
        }
    }

    public Map(int TYPE, int WIDTH, int HEIGHT, int SEED) {
        this.TYPE = TYPE;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.SEED = SEED;

        world = new Block[WIDTH][HEIGHT];
        buffer = new TETile[WIDTH][HEIGHT];
        creatures = new ArrayList<>();
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j< HEIGHT; ++j) {
                world[i][j] = BlockSet.BEDROCK;
                buffer[i][j] = Tileset.NOTHING;
            }
        }

        renderer = new TERenderer();
        random = new Random(SEED);

        switch (TYPE) {
            case 0 -> generator = new MapGenV1();
            default -> generator = new MapGenV1();
        }

        updateBuffer(generator.generate(this));

        renderer.initialize(WIDTH, HEIGHT);
    }

    /**
     * Update Map each tick
     * This function should be called each tick.
     * @return number of changed blocks
     */
    public int update() {
        Set<Position> set = generator.update(this);
        updateBuffer(set);
        return 0;
    }

    public int render() {
        renderer.renderFrame(buffer);
        return 0;
    }

    public static void main(String[] args) {
        BlockSet.init();
        Map map = new Map(0, 80, 60, 0);
        map.render();
        return;
    }
}
