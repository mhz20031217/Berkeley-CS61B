package byog.Creature;

import byog.Base.Position;
import byog.Block.Block;
import byog.TileEngine.TETile;

public abstract class Creature {
    private Position position;
    private int health;
    private boolean alive;
    protected TETile tile;

    Creature(int health, TETile tile) {
        this.tile = tile;
        assert (health > 0);
        alive = true;
        this.health = health;
    }

    public boolean isAlive() {
        return alive;
    }

    public int damage(int value) {
        health -= value;
        if (health < 0) health = 0;
        if (health == 0) alive = false;
        return health;
    }

    public int recover(int value) {
        if (!isAlive()) return health;
        return health += value;
    }

    public int blockEffect(Block block) {
        return health;
    }

    public int setPosition(Position pos) {
        this.position = pos;
        return 0;
    }

    public Position getPosition() {
        return position;
    }

    public TETile getTile() {
        return tile;
    }
}
