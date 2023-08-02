package byog.Map;
import byog.Block.Block;
import byog.Block.BlockSet;
import byog.Base.*;
import byog.Creature.Player;
import byog.TileEngine.Tileset;

import java.util.*;
import java.util.TreeSet;

import byog.Core.RandomUtils.*;

import static byog.Core.RandomUtils.uniform;

public class MapGenV1 extends MapGen {
    /**
     * The DENSITY of different areas of the map.
     * The higher the value, more different terrains will be generated.
     */
    private static final int DENSITY = 4;
    private static final int BUILDING_FACTOR = 64;
    private static final int BUILDING_AREA_THRESHOLD = 400;
    private static final int BUILDING_AREA_OVERLAP = 2;
    private static final int BUILDING_AREA_SINGLE_THRESHOLD = 40;
    private static final int BUILDING_COUNT_THRESHOLD = 200;
    private static final int PRECISION = 4;
    private static final int PLAYER_HEALTH = 5;

    private static final Block[] NATURAL = BlockSet.NATURAL;
    private static final Block[] ARTIFACTS = BlockSet.ARTIFACTS;
    private static final double
        L_MOUNTAIN_MIN = 0.35,
        L_MOUNTAIN_MAX = 1.0,
        L_WATER_MIN = -1.0,
        L_WATER_MAX = -0.2,
        L_SAND_MIN = -0.2,
        L_SAND_MAX = -0.15,
        L_GRASS_MIN = -0.15,
        L_GRASS_MAX = 0.35;
    private static final int dx[] = {0, 0, 1, -1},
                             dy[] = {1, -1, 0, 0};

    /**
     * Return an array of Positions as reference points according to the area and DENSITY.
     * @param random the random generator
     * @param width width
     * @param height height
     * @return an array of Positions as reference points
     */
    private Position[] randomReferences(Random random, int width, int height) {
        int count = width * height * DENSITY / BUILDING_FACTOR;
        Position[] array = new Position[count];
        for (int i = 0; i < count; ++i) {
            array[i] = new Position(random.nextInt(width), random.nextInt(height));
        }
        return array;
    }

    private int[][] voronoi(int width, int height, Position[] pos) {
        assert (pos != null && pos.length > 0);
        assert (width > 0 && height > 0);

        int[][] mapping = new int[width][height];

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                int bestn = LIMIT * LIMIT, bestp = 0;
                for (int k = 0; k < pos.length; ++k) {
                    int dx = pos[k].x - i, dy = pos[k].y - j;
                    int dist = dx*dx + dy+dy;
                    if (dist < bestn) {
                        bestn = dist;
                        bestp = k;
                    }
                }
                mapping[i][j] = bestp;
            }
        }

        return mapping;
    }

//    private int generateNature(TETile[][] world, Random random, int width, int height) {
//        int cnt = width * height;
//        Position[] ref = randomReferences(random, width, height);
//        int[] type = new int[ref.length];
//        for (int i = 0; i < ref.length; ++i) {
//            type[i] = random.nextInt(NATURAL_TILES.length);
//        }
//
//        int[][] mapping = voronoi(width, height, ref);
//
//        for (int i = 0; i < width; ++i) {
//            for (int j = 0; j < height; ++j) {
//                world[i][j] = NATURAL_TILES[type[mapping[i][j]]];
//            }
//        }
//        return cnt;
//    }

    private Block getTerrainTile(double level) {
        if (level < L_WATER_MAX) return BlockSet.WATER;
        if (level < L_SAND_MAX) return BlockSet.SAND;
        if (level < L_GRASS_MAX) return BlockSet.GRASS;
        return BlockSet.MOUNTAIN;
    }
    private Block getPlantTile(Block block, Random random) {
        if (!BlockSet.S_NATURAL.contains(block)) return block;
        if (block == BlockSet.WATER) return block;
        int r = random.nextInt(1000);
        if (block == BlockSet.GRASS) {
            if (r < 20) return BlockSet.FLOWER;
            if (r < 100) return BlockSet.TREE;
        }
        if (block == BlockSet.MOUNTAIN) {
            if (r < 50) return BlockSet.TREE;
            if (r < 60) return BlockSet.FLOWER;
        }
        return block;
    }
    private TreeSet<Position> generateTerrain(Block[][] world, Random random, int width, int height) {
        TreeSet<Position> set = new TreeSet<>();
        double[][] noiseMap = perlinMap(width, height);

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                world[i][j] = getTerrainTile(noiseMap[i][j]);
//                world[i][j] = getPlantTile(world[i][j], random);
                set.add(new Position(i, j));
            }
        }
        return set;
    }

    private TreeSet<Position> generatePlants(Block[][] world, Random random, int width, int height) {
        TreeSet<Position> set = new TreeSet<>();

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                world[i][j] = getPlantTile(world[i][j], random);
                set.add(new Position(i, j));
            }
        }
        return set;
    }

    /**
     * Simple single-point one-dimensional noise generator
     * @param x pos
     * @return noise
     * @author Hugo Elias
     * @see <a href="https://web.archive.org/web/20080724063449/http://freespace.virgin.net/hugo.elias/models/m_perlin.htm">Perlin Noise</a>
     */
    private double noise(int x) {
        x = (x<<13) ^ x;
        return ( 1.0 - ( (x * (x * x * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
    }

    private double noise(Position pos) {
        return noise(pos.x, pos.y);
    }

    private double noise(int x, int y) {
        int n = x + y * 57;
        return noise(n);
    }

    private double smoothNoise(int x) {
        return (noise(x-1) + noise(x+1)) / 4 + noise(x) / 2;
    }

    private double smoothNoise(Position pos) {
        int x = pos.x, y = pos.y;
        double corners = (
                noise(x-1, y-1) + noise(x+1, y+1) + noise(x-1, y+1) + noise(x+1, y-1)
        ) / 16;
        double sides = (
                noise(x-1, y) + noise(x+1, y) + noise(x, y-1) + noise(x, y+1)
        ) / 8;
        double center = noise(pos) / 4;
        return corners + sides + center;
    }

    private double perlinNoise(int x, int y, int octave) {
        assert (octave > 0 && octave < LIMIT);
        double total = 0;
        double persistence = 0.5 * (1 - 1.0 / DENSITY);

        int i;
        int frequency;
        double amplitude;
        for (i = 0, frequency = 1, amplitude = 1;
             i < octave;
             ++i, frequency *= 2, amplitude *= persistence) {
            total += smoothNoise(new Position(x * frequency, y * frequency)) * amplitude;
        }

        return total;
    }

    private double[][] perlinMap(int width, int height) {
        double[][] map = new double[width][height];

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                map[i][j] = perlinNoise(i, j, PRECISION);
            }
        }

        return map;
    }

    private boolean inRange(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean buildingOk(Block[][] world, int x, int y) {
        return world[x][y] != BlockSet.WATER && world[x][y] != BlockSet.MOUNTAIN;
    }

    private int buildingRange(Position ld, Position ru, Block[][] world, int width, int height, Position pos) {
        int x = pos.x, y = pos.y;
//        if (world[x][y] == BlockSet.WATER || world[x][y] == BlockSet.MOUNTAIN)
//            return 1;
        int lBound = x, rBound = x, uBound = y, dBound = y;

        int reg = 0;
        while (inRange(lBound, y, width, height) && (reg += buildingOk(world, lBound, y)?0:1) < BUILDING_AREA_OVERLAP) {
            lBound--;
        }
        lBound++;
        reg = 0;
        while (inRange(rBound, y, width, height) && (reg += buildingOk(world, rBound, y)?0:1) < BUILDING_AREA_OVERLAP) {
            rBound++;
        }
        rBound--;
        reg = 0;
        if (rBound - lBound <= 16 / DENSITY) {
            return 2;
        }

        lBound = (2 * lBound + x) / 3;
        rBound = (2 * rBound + x) / 3;
        x = (lBound + rBound) / 2;

        reg = 0;
        while (inRange(x, dBound, width, height) && (reg += buildingOk(world, x, dBound)?0:1) < BUILDING_AREA_OVERLAP) {
            dBound--;
        }
        dBound++;
        reg = 0;
        while (inRange(x, uBound, width, height) && (reg += buildingOk(world, x, uBound)?0:1) < BUILDING_AREA_OVERLAP) {
            uBound++;
        }
        uBound--;
        if (uBound - dBound <= 16 / DENSITY) {
            return 2;
        }

        uBound = (2 * uBound + y) / 3;
        dBound = (2 * dBound + y) / 3;
        y = (dBound + uBound) / 2;

        ld.x = lBound;
        ld.y = dBound;
        ru.x = rBound;
        ru.y = uBound;

        return 0;
    }

    private TreeSet<Position> generateFloor(Block[][] world, Random random, Position ld, Position ru) {
        TreeSet<Position> set = new TreeSet<>();
        int sx = ld.x, ex = ru.x + 1, sy = ld.y, ey = ru.y + 1;
        int width = ex - sx, height = ey -sy;
        int maxWidth = width, maxHeight = height;
        int count = width * height / BUILDING_FACTOR;

        int lastX = 0, lastY = 0;

        int dx = uniform(random, 3, maxWidth), dy = uniform(random, 3, maxHeight),
            px = uniform(random, width - dx), py = uniform(random, height - dy);
        int x, y;
        x = px + dx / 2;
        y = py + dy / 2;

        for (int i = sx + px; i < sx + px + dx; ++i) {
            for (int j = sy + py; j < sy + py +dy; ++j) {
                world[i][j] = BlockSet.FLOOR;
                set.add(new Position(i, j));
            }
        }

        int order = random.nextInt(2);
        if (order == 0) {
            for (int i = sx + lastX; i < x; ++i) {
                world[i][lastY] = BlockSet.FLOOR;
                set.add(new Position(i, lastY));
            }
            for (int j = sy + lastY; j < y; ++j) {
                world[x][j] = BlockSet.FLOOR;
                set.add(new Position(x, j));
            }
        } else {
            for (int j = sy + lastY; j < y; ++j) {
                world[lastX][j] = BlockSet.FLOOR;
                set.add(new Position(lastX, j));
            }
            for (int i = sx + lastX; i < x; ++i) {
                world[i][y] = BlockSet.FLOOR;
                set.add(new Position(i, y));
            }
        }

        lastX = x;
        lastY = y;

        for (int k = 0; k < count; ++k) {
            dx = uniform(random, 3, maxWidth); dy = uniform(random, 3, maxHeight);
            px = uniform(random, width - dx); py = uniform(random, height - dy);
            x = px + dx / 2;
            y = py + dy / 2;
            for (int i = sx + px; i < sx + px + dx; ++i) {
                for (int j = sy + py; j < sy + py +dy; ++j) {
                    world[i][j] = BlockSet.FLOOR;
                    set.add(new Position(i, j));
                }
            }
            order = random.nextInt(2);
            if (order == 0) {
                for (int i = sx + lastX; i < x; ++i) {
                    world[i][lastY] = BlockSet.FLOOR;
                    set.add(new Position(i, lastY));
                }
                for (int j = sy + lastY; j < y; ++j) {
                    world[x][j] = BlockSet.FLOOR;
                    set.add(new Position(x, j));
                }
            } else {
                for (int j = sy + lastY; j < y; ++j) {
                    world[lastX][j] = BlockSet.FLOOR;
                    set.add(new Position(lastX, j));
                }
                for (int i = sx + lastX; i < x; ++i) {
                    world[i][y] = BlockSet.FLOOR;
                    set.add(new Position(i, y));
                }
            }

            lastX = x;
            lastY = y;
        }

        return set;
    }

    private HashSet<Position> generateWall(Block[][] world, Random random, Position ld, Position ru) {
        HashSet<Position> set = new HashSet<>();

        for (int i = ld.x; i < ru.x; ++i) {
            for (int j = ld.y; j < ru.y; ++j) {
                if (world[i][j] != BlockSet.FLOOR) continue;
                boolean flag = false;
                for (int k = 0; k < 4; ++k) {
                    int nx = i +dx[k], ny = j + dy[k];
                    if (!inRange(nx, ny, world.length, world[0].length) || world[nx][ny] == BlockSet.FLOOR || world[nx][ny] == BlockSet.WALL) continue;
                    flag = true;
                }
                if (flag) {
                    world[i][j] = BlockSet.WALL;
                    set.add(new Position(i, j));
                }
            }
        }

        for (Iterator<Position> iterator = set.iterator(); iterator.hasNext();) {
            Position pos = iterator.next();
            int cntWall, cntFloor, cntBound;
            cntWall = cntFloor = cntBound = 0;
            for (int k = 0; k < 4; ++k) {
                int nx = pos.x + dx[k], ny = pos.y + dy[k];
                if (!inRange(nx, ny, world.length, world[0].length)) {
                    cntBound++;
                    break;
                }
                if (world[nx][ny] == BlockSet.FLOOR)
                    cntFloor++;
                if (world[nx][ny] == BlockSet.WALL)
                    cntWall++;
            }
            if (cntBound > 0) continue;
            if (cntWall == 2 && cntFloor == 1) {
                world[pos.x][pos.y] = BlockSet.FLOOR;
                break;
            }
        }

        return set;
    }

    private TreeSet<Position> generateArtifacts(Block[][] world, Random random, int width, int height) {
        TreeSet<Position> set = new TreeSet<>();

        Position[] ref = randomReferences(random, width, height);
        int count;
        Position ld[] = new Position[ref.length];
        Position ru[] = new Position[ref.length];
        int area[] = new int[ref.length];

        for (int i = 0; i < ref.length; ++i) {
            ld[i] = new Position();
            ru[i] = new Position();
            if (buildingRange(ld[i], ru[i], world, width, height, ref[i]) != 0) {
                area[i] = -1;
            }
            area[i] = (ru[i].x - ld[i].x) * (ru[i].y - ld[i].y);
        }

        for (int i = 0; i < ref.length-1; ++i) {
            for (int j = 0; j < ref.length-1; ++j) {
                if (area[j] >= area[j+1]) continue;
                int tmpi;
                Position tmpp;
                tmpi = area[j]; area[j] = area[j+1]; area[j+1] = tmpi;
                tmpp = ld[j]; ld[j] = ld[j+1]; ld[j+1] = tmpp;
                tmpp = ru[j]; ru[j] = ru[j+1]; ru[j+1] = tmpp;
                tmpp = ref[j]; ref[j] = ref[j+1]; ref[j+1] = tmpp;
            }
        }

        for (count = 0; count < ref.length; ++count) {
            if (area[count] <= 0) break;
        }

//        System.out.println(count);

        int i = 0;
        while (i < count && area[i] > BUILDING_AREA_THRESHOLD / DENSITY) ++i;
        count = Math.min(count, i + width * height / BUILDING_COUNT_THRESHOLD);
        while (i < count) {
            set.addAll(generateFloor(world, random, ld[i], ru[i]));
            set.addAll(generateWall(world, random, ld[i], ru[i]));
            ++i;
        }

        return set;
    }

    private int generatePlayers(Player[] players, Block[][] world, int width, int height, int number_of_players) {
        TreeSet<Position> set = new TreeSet<>();

        for (int k = 0; k < number_of_players; ++k) {
            if (players[k] == null) players[k] = new Player(PLAYER_HEALTH);
        }

        int cnt = 0;
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j <height; ++j) {
                if (world[i][j].isBlocking()) continue;
                players[cnt].setPosition(new Position(i, j));
                cnt++;
                if (cnt == number_of_players) break;
            }
            if (cnt == number_of_players) break;
        }
        if (cnt != number_of_players) return 1;

        return 0;
    }

    /**
     * Generate an initial world.
     *
     * @param map the Map to modify
     * @return count of successfully generated blocks
     */
    public TreeSet<Position> generate(Map map) {
        Random random = map.random;

        TreeSet<Position> set = new TreeSet<>();
        set.addAll(generateTerrain(map.world, random, map.WIDTH, map.HEIGHT));
        set.addAll(generateArtifacts(map.world, random, map.WIDTH, map.HEIGHT));
        set.addAll(generatePlants(map.world, random, map.WIDTH, map.HEIGHT));

        generatePlayers(map.players, map.world, map.WIDTH, map.HEIGHT, map.players.length);

        return set;
    }

    /**
     * Update the terrain by one tick
     *
     * @param map the Map to modify
     * @return count of modified blocks
     */
    public TreeSet<Position> update(Map map) {
        return new TreeSet<>();
    }

}
