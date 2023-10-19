package Simulation.Organisms.Plants;

import Simulation.Vector2D;
import Simulation.Organisms.Plant;
import Simulation.Organisms.Organism;
import java.awt.*;

public class Grass extends Plant {
    public static final int STRENGTH = 0;

    public Grass(Vector2D position) {
        super(position, STRENGTH);
    }

    @Override
    public String toString(){
        return "Grass";
    }

    @Override
    public Color draw() {
        return new Color(165, 211, 142);
    }

    @Override
    public Organism copy() {
        return new Grass(position);
    }
}