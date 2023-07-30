package byog.Block;

import byog.Block.Plant.Flower;
import byog.Block.Plant.Tree;
import byog.Block.Terrain.*;
import byog.Block.Building.*;
import byog.TileEngine.TETile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockSet {
    public static final Bedrock BEDROCK = new Bedrock();
    public static final Grass GRASS = new Grass();
    public static final Flower FLOWER = new Flower();
    public static final Tree TREE = new Tree();
    public static final Mountain MOUNTAIN = new Mountain();
    public static final Sand SAND = new Sand();
    public static final Water WATER = new Water();
    public static final Door DOOR = new Door();
    public static final Floor FLOOR = new Floor();
    public static final Wall WALL = new Wall();

    public static final Block[] NATURAL = {
            GRASS, MOUNTAIN, SAND, WATER, TREE, FLOWER
    };
    public static final Block[] ARTIFACTS = {
            FLOOR, WALL, DOOR
    };
    public static final Block[] TERRAIN = {
            GRASS, SAND, WATER, MOUNTAIN
    };
    public static final Block[] PLANTS = {
            FLOWER, TREE
    };

    public static final Set<Block> S_NATURAL = new HashSet<>();
    public static final Set<Block> S_ARTIFACTS = new HashSet<>();
    public static final Set<Block> S_ANIMALS = new HashSet<>();
    public static final Set<Block> S_TERRAIN = new HashSet<>();
    public static final Set<Block> S_PLANTS = new HashSet<>();

    /**
     * Initialize the Tileset
     * This function must be called before Map generation
     * @return
     */
    public static int init() {
        S_NATURAL.addAll(Arrays.asList(NATURAL));
        S_ARTIFACTS.addAll(Arrays.asList(ARTIFACTS));
        S_TERRAIN.addAll(Arrays.asList(TERRAIN));
        S_PLANTS.addAll(Arrays.asList(PLANTS));

        return 0;
    }
}
