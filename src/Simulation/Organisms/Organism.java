package Simulation.Organisms;

import Simulation.Vector2D;
import Simulation.World;
import java.awt.*;

abstract public class Organism {
    public Vector2D getPosition(){
        return position;
    }

    public void setPosition(Vector2D position){
        this.position = position;
    }

    public int getInitiative(){
        return initiative;
    }

    public int getStrength(){
        return strength;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public boolean isAlive(){
        return alive;
    }

    public void kill(){
        world.getNotificationManager().add(toString() + " dies");
        alive = false;
    }

    public int getAge(){
        return age;
    }

    public void activateSpecialFeature(Organism organism) {
    }

    public boolean isAttackReflected(Organism organism){
        return false;
    }

    public boolean isEscaped(){
        return false;
    }

    public boolean escape(){
        if(isEscaped()){
            Vector2D newPosition = world.getNewEmptyPlace(position);

            if(newPosition == position){
                return false;
            }

            setPosition(newPosition);

            return true;
        }
        return false;
    }

    public void incrementAge() {
        age++;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    abstract public String toString();

    public abstract void action();
    public abstract void collision();
    public abstract Color draw();
    public abstract void newRound();
    public abstract Organism copy();

    protected int strength;
    protected int initiative;
    protected int age = 0;
    protected boolean alive = true;
    protected World world;
    protected Vector2D position;

    protected Organism(Vector2D position, int strength, int initiative){
        this.position = position;
        this.strength = strength;
        this.initiative = initiative;
    }

    protected boolean hasGoodSenseOfSmell(){
        return false;
    }
}
