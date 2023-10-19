package Simulation.Organisms.Plants;

import Simulation.Vector2D;
import Simulation.Organisms.Plant;
import java.awt.*;

public class Dandelion extends Plant {
    public static final int STRENGTH = 0;
    public static final int CHANCES_TO_SPREAD = 3;

    public Dandelion(Vector2D position) {
        super(position, STRENGTH);
    }

    @Override
    public void action(){
        for(int i = 0; i < CHANCES_TO_SPREAD; i++){
            spread();
        }
    }

    @Override
    public String toString() {
        return "Dandelion";
    }

    @Override
    public Color draw() {
        return new Color(255, 224, 79);
    }

    @Override
    public Dandelion copy() {
        return new Dandelion(position);
    }
}