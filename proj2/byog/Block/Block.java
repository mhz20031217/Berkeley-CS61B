package byog.Block;

import byog.Block.Building.Wall;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public interface Block {
    Block getReplacement();
    boolean isBlocking();

    TETile getTile();
}
