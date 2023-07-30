package byog.Map;

import byog.Base.Position;
import byog.TileEngine.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public abstract class MapGen {
    public final int LIMIT = 1<<12;


    /**
     * Generate an initial world.
     * @param map the Map to modify
     * @return count of successfully generated blocks
     */
    public abstract Set<Position> generate(Map map);

    /**
     * Update the terrain by one tick
     * @param map the Map to modify
     * @return count of modified blocks
     */
    public abstract Set<Position> update(Map map);
}

