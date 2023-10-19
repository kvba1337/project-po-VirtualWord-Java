package Simulation.Organisms;

import Simulation.Organisms.Animals.Wolf;
import Simulation.Vector2D;
import Simulation.World;
import static java.lang.Math.random;

abstract public class Animal extends Organism {
    public Animal(Vector2D position, int strength, int initiative){
        super(position,strength,initiative);
    }

    @Override
    public void action() {
        randomMove(1);
    }

    @Override
    public void collision() {
        Organism collidingOrganism = world.getCollidingOrganism(this);
        if(collidingOrganism == null) return;

        if(collidingOrganism.toString().equals(toString())){
            clone((Animal) collidingOrganism);
        }
        else {
            fight(collidingOrganism);
        }
    }

    @Override
    public void newRound() {
        isCloned = false;
    }

    protected void randomMove(int reach){
        if(hasGoodSenseOfSmell() && isEveryNeighBorStronger()){
            return;
        }

        int[] coordinates = {-1 * reach, 0, reach};
        Vector2D newPosition = new Vector2D(0,0);
        Vector2D previousPosition = new Vector2D(position.getY(), position.getX());

        do {
            int randX = coordinates[(int) (random() * 3)];
            int randY = coordinates[(int) (random() * 3)];
            newPosition = new Vector2D(randY,randX);

            changePosition(newPosition);

        } while(previousPosition.equals(position) ||
                (hasGoodSenseOfSmell() &&
                        world.getCollidingOrganism(this) != null &&
                        world.getCollidingOrganism(this).getStrength() > getStrength()));
//        if (this instanceof Wolf) {
//            world.getNotificationManager().add("Wolf moved");
//        }
    }

    protected void changePosition(Vector2D newPosition){
        if(world.getType() == World.Type.hexagonal && (newPosition.equals(new Vector2D(-1,-1)) ||
                newPosition.equals(new Vector2D(1,-1)))){
            return;
        }

        if(!position.add(newPosition).isOutOfBounds(world.getHeight(), world.getWidth())){
            previousPosition = new Vector2D(position.getY(), position.getX());
            position.addInPlace(newPosition);
        }
    }

    private boolean isCloned = false;
    private Vector2D previousPosition;

    private void fight(Organism attacker){
        if(escape() || attacker.escape()) return;

        if(getStrength() < attacker.getStrength()){
            if(isAttackReflected(attacker)){
                moveBack();
                return;
            }

            world.getNotificationManager().add(attacker.toString() + " kills " + toString());
            kill();
            activateSpecialFeature(attacker);

        }
        else {
            if(attacker.isAttackReflected(this)){
                moveBack();
                return;
            }

            world.getNotificationManager().add(toString() + " kills " + attacker.toString());
            attacker.kill();
            attacker.activateSpecialFeature(this);
        }
    }

    private void clone(Animal other){
        if(other.getAge() == 0){
            return;
        }

        Organism organism = copy();
        moveBack();
        Vector2D newPosition = world.getNewEmptyPlace(other.getPosition());

        if(newPosition.equals(other.getPosition()) || isCloned || other.isCloned){
            return;
        }

        organism.setPosition(newPosition);
        organism.setAge(-1);
        world.addOrganism(organism);
        isCloned = true;
        other.isCloned = true;
    }

    private void moveBack(){
        setPosition(previousPosition);
    }

    private boolean isEveryNeighBorStronger(){
        for(int y = -1; y <= 1; y++){
            for(int x = -1; x <= 1; x++){
                Vector2D position = new Vector2D(y,x);
                Organism organism = world.getOrganismAtPosition(this.position.add(position));

                if(organism != this && (organism == null || organism.getStrength() <= strength)){
                    return false;
                }
            }
        }
        return true;
    }
}
