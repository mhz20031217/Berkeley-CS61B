package byog.Block.Building;

import byog.Block.Block;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Door implements Block {
    public static final int hardness = 8;
    public TETile tile;
    public static final String id = "Door";
    private boolean locked;

    public Door() {
        locked = false;
        tile = Tileset.UNLOCKED_DOOR;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock() {
        locked = true;
        tile = Tileset.LOCKED_DOOR;
    }

    public void unlock() {
        locked = false;
        tile = Tileset.UNLOCKED_DOOR;
    }

    @Override
    public Block getReplacement() {
        return new Floor();
    }

    @Override
    public boolean isBlocking() {
        return isLocked();
    }

    @Override
    public TETile getTile() {
        return tile;
    }

}
