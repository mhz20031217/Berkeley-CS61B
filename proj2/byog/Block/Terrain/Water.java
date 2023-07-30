package byog.Block.Terrain;

import byog.Block.Block;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Water implements Block {
    public static final int hardness = 0;
    public static final TETile tile = Tileset.WATER;
    public static final String id = "Water";

    @Override
    public Block getReplacement() {
        return new Grass();
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
