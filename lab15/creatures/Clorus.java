package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {
    private static final double REPLICATE_ENERGY_RATIO = 0.5;
    private static final Random random = new Random();
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        double nEnergy = energy * REPLICATE_ENERGY_RATIO;
        double oEnergy = energy - nEnergy;
        energy = nEnergy;
        return new Clorus(oEnergy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Action stayAction = new Action(Action.ActionType.STAY);
        List<Direction> spaces = getNeighborsOfType(neighbors, "empty");
        List<Direction> food = getNeighborsOfType(neighbors, "plip");
        if (spaces.size() == 0) {
            return stayAction;
        } else if (food.size() > 0) {
            int r = random.nextInt(food.size());
            return new Action(Action.ActionType.ATTACK, food.get(r));
        } else if (energy >= 1) {
            int r = random.nextInt(spaces.size());
            return new Action(Action.ActionType.REPLICATE, spaces.get(r));
        } else {
            int r = random.nextInt(spaces.size());
            return new Action(Action.ActionType.MOVE, spaces.get(r));
        }
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }
}
