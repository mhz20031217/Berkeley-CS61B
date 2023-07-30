package byog.Block.Terrain;

import byog.Block.Block;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Sand implements Block {
    public static final int hardness = 4;
    public static final TETile tile = Tileset.SAND;
    public static final String id = "Sand";

    @Override
    public Block getReplacement() {
        return new Water();
    }

    @Override
    public boolean isBlocking() {
        return false;
    }

    @Override
    public TETile getTile() {
        return tile;
    }
}
