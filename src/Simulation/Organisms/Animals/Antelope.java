package Simulation.Organisms.Animals;

import Simulation.Vector2D;
import Simulation.Organisms.Animal;
import java.awt.*;
import static java.lang.Math.random;

public class Antelope extends Animal {
    public static final int STRENGTH = 4;
    public static final int INITIATIVE = 4;
    public static final int REACH = 2;
    public final double CHANCE_TO_ESCAPE = 0.5;

    public Antelope(Vector2D position) {
        super(position, STRENGTH, INITIATIVE);
    }

    @Override
    public void action(){
        randomMove(REACH);
    }

    @Override
    public String toString() {
        return "Antelope";
    }

    @Override
    public Color draw() {
        return new Color(148, 96, 27);
    }

    @Override
    public Antelope copy() {
        return new Antelope(position);
    }

    @Override
    public boolean isEscaped(){
        return random() < CHANCE_TO_ESCAPE;
    }
}
