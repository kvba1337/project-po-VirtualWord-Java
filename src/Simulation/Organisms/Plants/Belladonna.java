package Simulation.Organisms.Plants;

import Simulation.Vector2D;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Plant;
import java.awt.*;

public class Belladonna extends Plant {
    public static int STRENGTH = 0;

    public Belladonna(Vector2D position) {
        super(position, STRENGTH);
    }

    @Override
    public void activateSpecialFeature(Organism organism){
        organism.kill();
    }

    @Override
    public String toString() {
        return "Belladonna";
    }

    @Override
    public Color draw() {
        return new Color(102, 1, 159);
    }

    @Override
    public Belladonna copy() {
        return new Belladonna(position);
    }
}
