package byog.Map;

import byog.Base.Position;
import byog.Block.Block;
import byog.Block.BlockSet;
import byog.Creature.Creature;
import byog.Creature.Player;
import byog.TileEngine.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Map {
    private final int[] dx = {0, 0, 1, -1},
                        dy = {1, -1, 0, 0};
    final int TYPE, WIDTH, HEIGHT, SEED;
    final Block[][] world;
    final ArrayList<Creature> creatures;
    final Player[] players;
    private int currentPlayer;
    final TETile[][] buffer;
    private final TERenderer renderer;
    final Random random;
    private final MapGen generator;

    public boolean inRange(int x, int y) {
        return 0 <= x && x < WIDTH && 0 <= y && y < HEIGHT;
    }
    public boolean inRange(Position pos) {
        return inRange(pos.x, pos.y);
    }

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

    private void updateBuffer() {
        updateBuffer(new Position(0, 0), new Position(WIDTH, HEIGHT));
    }

    public Map(int TYPE, int WIDTH, int HEIGHT, int SEED, int NUMBER_OF_PLAYERS, TERenderer render) {
        this.TYPE = TYPE;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.SEED = SEED;
        this.currentPlayer = 0;

        world = new Block[WIDTH][HEIGHT];
        buffer = new TETile[WIDTH][HEIGHT];
        creatures = new ArrayList<>();
        players = new Player[NUMBER_OF_PLAYERS];
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j< HEIGHT; ++j) {
                world[i][j] = BlockSet.BEDROCK;
                buffer[i][j] = Tileset.NOTHING;
            }
        }

        this.renderer = render;
        random = new Random(SEED);

        switch (TYPE) {
            case 0 -> generator = new MapGenV1();
            default -> generator = new MapGenV1();
        }

        Set<Position> set = generator.generate(this);

        updateBuffer(set);
        updateCreatureOverlay();

        render.initialize(WIDTH, HEIGHT);
    }

    private int updateCreatureOverlay() {
        for (Player player: players) {
            Position pos = player.getPosition();
            buffer[pos.x][pos.y] = player.getTile();
        }
        return 0;
    }

    /**
     * Update Map each tick
     * This function should be called each tick.
     * @return number of changed blocks
     */
    public int update() {
        generator.update(this);
        updateBuffer();
        updateCreatureOverlay();
        return 0;
    }

    public int render() {
        renderer.renderFrame(buffer);
        return 0;
    }

    public int control(char ch) {
        ch = Character.toLowerCase(ch);

        int k;
        switch (ch) {
            case 'w': k = 0;
            break;
            case 'a': k = 3;
            break;
            case 's': k = 1;
            break;
            case 'd': k = 2;
            break;
            default: return 1;
        }

        Position pos = players[currentPlayer].getPosition();
        pos = new Position(pos.x + dx[k], pos.y + dy[k]);
        if (!inRange(pos)) return 1;
        if (world[pos.x][pos.y].isBlocking()) {
            return 1;
        } else {
            players[currentPlayer].setPosition(pos);
            update();
        }
        return 0;
    }

    public static void main(String[] args) {
        TERenderer render = new TERenderer();
        Map map = new Map(0, 100, 60, 47890, 1, render);
        map.render();
        return;
    }
}
