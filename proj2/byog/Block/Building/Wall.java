package byog.Block.Building;

import byog.Block.Block;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Wall implements Block {
    public static final int hardness = 10;
    public static final TETile tile = Tileset.WALL;
    public static final String id = "Wall";

    @Override
    public Block getReplacement() {
        return new Floor();
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
