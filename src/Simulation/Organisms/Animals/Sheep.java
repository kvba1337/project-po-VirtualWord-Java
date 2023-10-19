package Simulation.Organisms.Animals;

import Simulation.Vector2D;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Animal;
import java.awt.*;

public class Sheep extends Animal {
    public static final int STRENGTH = 4;
    public static final int INITIATIVE = 4;

    public Sheep(Vector2D position) {
        super(position, STRENGTH, INITIATIVE);
    }

    @Override
    public String toString() {
        return "Sheep";
    }

    @Override
    public Color draw() {
        return new Color(130, 130, 130);
    }

    @Override
    public Organism copy() {
        return new Sheep(position);
    }
}
