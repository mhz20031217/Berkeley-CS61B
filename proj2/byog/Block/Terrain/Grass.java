package byog.Block.Terrain;

import byog.Block.Block;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Grass implements Block {
    public static final int hardness = 3;
    public static final TETile tile = Tileset.GRASS;
    public static final String id = "Grass";


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
