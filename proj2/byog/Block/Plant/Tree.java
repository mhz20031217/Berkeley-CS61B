package byog.Block.Plant;

import byog.Block.Block;
import byog.Block.Terrain.Grass;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Tree implements Block {
    public static final int hardness = 4;
    public static final TETile tile = Tileset.TREE;
    public static final String id = "Tree";

    public Block getReplacement() {
        return new Grass();
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public TETile getTile() {
        return tile;
    }
}
