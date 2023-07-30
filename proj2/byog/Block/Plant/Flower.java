package byog.Block.Plant;

import byog.Block.Block;
import byog.Block.Terrain.Grass;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Flower implements Block {
    public static final int hardness = 0;
    public static final TETile tile = Tileset.FLOWER;
    public static final String id = "Flower";

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
