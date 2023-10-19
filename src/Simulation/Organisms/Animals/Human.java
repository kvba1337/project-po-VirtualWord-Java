package Simulation.Organisms.Animals;

import Simulation.Vector2D;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Animal;
import java.awt.*;
import static java.lang.Math.random;

public class Human extends Animal {
    public static final int STRENGTH = 5;
    public static final int INITIATIVE = 4;
    public static final int SPECIAL_ABILITY_COOLDOWN = 6;
    public static final int SPECIAL_ABILITY_COOLDOWN_SECOND_CHANCE = 2;
    public static final double SPECIAL_ABILITY_PROPABILITY = 0.5;

    public Human(Vector2D position) {
        super(position, STRENGTH, INITIATIVE);
    }

    @Override
    public void action(){
        int reach;

        if(specialAbilityCooldown <= 0){
            reach = 1;
        }
        else if(specialAbilityCooldown > SPECIAL_ABILITY_COOLDOWN_SECOND_CHANCE){
            reach = Antelope.REACH;
        }
        else {
            if(random() < SPECIAL_ABILITY_PROPABILITY){
                reach = Antelope.REACH;
            }
            else {
                reach = 1;
            }
        }

        switch(world.popMove()){
            case UP:
                changePosition(new Vector2D(reach * -1, 0));
                break;

            case DOWN:
                changePosition(new Vector2D(reach, 0));
                break;

            case RIGHT:
                changePosition(new Vector2D(0, reach));
                break;

            case LEFT:
                changePosition(new Vector2D(0, -1 * reach));
                break;

            case SPECIAL_ABILITY:
                if(specialAbilityCooldown <= -5){
                    specialAbilityCooldown = SPECIAL_ABILITY_COOLDOWN;
                    world.getNotificationManager().add("Special ability activated.");
                }
                else {
                    world.getNotificationManager().add("Special ability is on cooldown.");
                }
                break;
        }

        specialAbilityCooldown--;
    }

    public void setSpecialAbilityCooldown(int specialAbilityCooldown){
        this.specialAbilityCooldown = specialAbilityCooldown;
    }

    @Override
    public String toString() {
        return "Human";
    }

    @Override
    public Color draw() {
        return new Color(134, 134, 57);
    }

    public int getSpecialAbilityCooldown() {
        return specialAbilityCooldown;
    }

    @Override
    public Organism copy() {
        return new Human(position);
    }

    private int specialAbilityCooldown = -5;
}
