package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import org.hamcrest.internal.SelfDescribingValueIterator;

import java.awt.Color;
import java.util.Map;
import java.util.List;
import java.util.Random;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author Josh Hug
 */
public class Plip extends Creature {

    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;
    private static final double MAX_ENERGY = 2;
    /** energy ratio remain for itself */
    private static final double REPLICATE_ENERGY_RATIO = 0.5;
    private static final Random random = new Random();

    /** creates plip with energy equal to E. */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = (int) ((e / MAX_ENERGY) * (255 - 63) + 63);
        b = 76;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        g = (int) ((energy / MAX_ENERGY) * (255 - 63) + 63);
        return color(r, g, b);
    }

    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.15;
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy = Math.min(energy + 0.2, MAX_ENERGY);
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Plip replicate() {
        double nEnergy = energy * REPLICATE_ENERGY_RATIO;
        double oEnergy = energy - nEnergy;
        Plip ret = new Plip(oEnergy);
        this.energy = nEnergy;
        return ret;
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> spaces = getNeighborsOfType(neighbors, "empty");
        List<Direction> enermies = getNeighborsOfType(neighbors, "clorus");
        if (spaces.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (energy >= 1) {
            int r = random.nextInt(spaces.size());
            return new Action(Action.ActionType.REPLICATE, spaces.get(r));
        } else if (enermies.size() > 0) {
            int r = random.nextInt(2);
            if (r == 0) {
                return new Action(Action.ActionType.STAY);
            } else {
                r = random.nextInt(spaces.size());
                return new Action(Action.ActionType.MOVE, spaces.get(r));
            }
        }
        return new Action(Action.ActionType.STAY);
    }

}
