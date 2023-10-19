package Simulation.Organisms.Animals;

import Simulation.Vector2D;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Animal;
import java.awt.*;

public class Fox extends Animal {
    public static final int STRENGTH = 3;
    public static final int INITIATIVE = 7;

    public Fox(Vector2D position) {
        super(position, STRENGTH, INITIATIVE);
    }

    @Override
    public String toString() {
        return "Fox";
    }

    @Override
    public Color draw() {
        return new Color(255, 134, 0);
    }

    @Override
    public Organism copy() {
        return new Fox(position);
    }

    @Override
    protected boolean hasGoodSenseOfSmell(){
        return true;
    }
}
