package byog.Block.Terrain;

import byog.Block.Block;
import byog.Block.Building.Floor;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Mountain implements Block {
    public static final int hardness = 3;
    public static final TETile tile = Tileset.MOUNTAIN;
    public static final String id = "Mountain";

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
