package Simulation.Organisms.Animals;

import Simulation.Vector2D;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Animal;
import java.awt.*;
import static java.lang.Math.random;

public class Turtle extends Animal {
    public static final int STRENGTH = 2;
    public static final int INITIATIVE = 1;
    public static final double CHANCE_TO_MOVE = 0.25;
    public static final double MAX_STRENGTH_TO_REFLECT_ATTACK = 5;

    public Turtle(Vector2D position) {
        super(position, STRENGTH, INITIATIVE);
    }

    @Override
    public void action(){
        if(random() < CHANCE_TO_MOVE){
            randomMove(1);
        }
    }

    @Override
    public boolean isAttackReflected(Organism other){
        return other.getStrength() < MAX_STRENGTH_TO_REFLECT_ATTACK;
    }

    @Override
    public String toString() {
        return "Turtle";
    }

    @Override
    public Color draw() {
        return new Color(21, 121, 28);
    }

    @Override
    public Turtle copy() {
        return new Turtle(position);
    }
}
