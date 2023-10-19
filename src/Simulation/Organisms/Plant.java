package Simulation.Organisms;

import Simulation.Vector2D;
import static java.lang.Math.random;

public abstract class Plant extends Organism {
    static final int DEFAULT_INITIATIVE = 0;
    static final double CHANCE_TO_SPREAD = 0.05;

    public Plant(Vector2D position, int strength) {
        super(position, strength, DEFAULT_INITIATIVE);
    }

    @Override
    public void action() {
        spread();
    }

    @Override
    public void collision() {
    }

    @Override
    public void newRound() {
    }

    protected void spread(){
        if(random() < CHANCE_TO_SPREAD){
            Vector2D newPosition = world.getNewEmptyPlace(position);

            if(newPosition == position) return;

            Organism org = copy();
            org.setAge(0);
            org.setPosition(newPosition);
            world.addOrganism(org);
        }
    }
}
