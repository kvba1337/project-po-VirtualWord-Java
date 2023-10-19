package Simulation.Organisms.Plants;

import Simulation.Vector2D;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Plant;
import java.awt.*;

public class Guarana extends Plant {
    public static final int STRENGTH = 0;
    public static final int ADDITIONAL_STRENGTH = 3;

    public Guarana(Vector2D position) {
        super(position, STRENGTH);
    }

    @Override
    public void activateSpecialFeature(Organism organism){
        organism.setStrength(organism.getStrength() + ADDITIONAL_STRENGTH);
    }

    @Override
    public String toString() {
        return "Guarana";
    }

    @Override
    public Color draw() {
        return new Color(255, 57, 57);
    }

    @Override
    public Guarana copy() {
        return new Guarana(position);
    }
}