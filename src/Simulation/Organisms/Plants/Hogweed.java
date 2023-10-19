package Simulation.Organisms.Plants;

import Simulation.Vector2D;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Plant;
import Simulation.Organisms.Animal;
import java.awt.*;

public class Hogweed extends Plant {
    public static int STRENGTH = 0;

    public Hogweed(Vector2D position) {
        super(position, STRENGTH);
    }

    @Override
    public void activateSpecialFeature(Organism organism){
        organism.kill();
    }

    @Override
    public void action(){
        for(int y = -1; y <= 1; y++){
            for(int x = -1; x <= 1; x++){
                Organism organism = world.getOrganismAtPosition(position.add(new Vector2D(y,x)));

                if(organism instanceof Animal){
                    organism.kill();
                }
            }
        }
        super.action();
    }

    @Override
    public String toString() {
        return "Hogweed";
    }

    @Override
    public Color draw() {
        return new Color(255, 255, 255);
    }

    @Override
    public Hogweed copy() {
        return new Hogweed(position);
    }
}