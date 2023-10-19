package Simulation.Organisms.Animals;

import Simulation.Vector2D;
import Simulation.Organisms.Animal;
import java.awt.*;

public class Wolf extends Animal {
    public static final int STRENGTH = 9;
    public static final int INITIATIVE = 5;

    public Wolf(Vector2D position) {
        super(position, STRENGTH, INITIATIVE);
    }

    @Override
    public String toString() {
        return "Wolf";
    }

    @Override
    public Color draw() {
        return new Color(70, 64, 64);
    }

    @Override
    public Wolf copy() {
        return new Wolf(position);
    }
}
