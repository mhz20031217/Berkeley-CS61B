package byog.Block.Building;

import byog.Block.Block;
import byog.Block.Terrain.Grass;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Floor implements Block {
    public static final int hardness = 8;
    public static final TETile tile = Tileset.FLOOR;
    public static final String id = "Floor";

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
