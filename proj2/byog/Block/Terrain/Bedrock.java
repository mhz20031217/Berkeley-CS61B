package byog.Block.Terrain;

import byog.Block.Block;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Bedrock implements Block {
    public static final int hardness = Integer.MAX_VALUE;
    public static final TETile tile = Tileset.NOTHING;
    public static final String id = "Bedrock";

    @Override
    public Block getReplacement() {
        return new Bedrock();
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
