package byog.Creature;

import byog.TileEngine.Tileset;

public class Player extends Creature{
    public Player(int health) {
        super(health, Tileset.PLAYER);
    }


}
