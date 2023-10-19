package Simulation;

import Simulation.Organisms.Organism;
import Simulation.Organisms.Plants.*;
import Simulation.Organisms.Animals.*;
import java.util.Arrays;
import java.util.Vector;

public class World {
    public static int BOARD_SIZE_X = 30;
    public static int BOARD_SIZE_Y = 30;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Organism getCollidingOrganism(Organism organism) {
        for(Organism organism2 : organisms){
            if(organism.getPosition().equals(organism2.getPosition())
                    && organism != organism2
                    && organism.isAlive()){
                return organism2;
            }
        }
        return null;
    }

    public Vector2D getNewEmptyPlace(Vector2D position) {
        for(int dy = -1; dy <= 1; dy++){
            for(int dx = -1; dx <= 1; dx++){
                Vector2D newPosition = new Vector2D(position.getY() + dy, position.getX() + dx);

                if(!newPosition.equals(position)
                        && getOrganismAtPosition(newPosition) == null
                        && !newPosition.isOutOfBounds(height, width)){
                    return newPosition;
                }
            }
        }
        return position;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Type getType() {
        return type;
    }

    public enum Move {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        SPECIAL_ABILITY,
        STOP
    }

    public enum Type {
        cartesian,
        hexagonal
    }

    public World(int height, int width, Type type){
        this.height = height;
        this.width = width;
        organisms = new Vector<>();
        notificationManager = new NotificationManager();
        this.type = type;
    }

    public void nextRound(){
        announceNewRound();
        roundNumber++;
        moveOrganisms();
        getRidOfDeadOrganisms();
    }

    public void addOrganism(Organism organism){
        organism.setAge(organism.getAge()+1);
        organism.setWorld(this);
        organisms.add(organism);
    }

    public Organism getOrganismAtPosition(Vector2D position){
        Organism searchedOrganism = null;
        for(Organism org : organisms){
            if(org.getPosition().equals(position) && org.isAlive()){
                if(searchedOrganism == null || searchedOrganism.getStrength() < org.getStrength()){
                    searchedOrganism = org;
                }
            }
        }
        return searchedOrganism;
    }

    public void setMove(Move move){
        this.move = move;
    }

    public Move popMove(){
        Move current = move;
        move = Move.STOP;

        return current;
    }

    public Move getMove(){
        return move;
    }

    public Vector<Organism> getOrganisms() {
        return organisms;
    }

    public static World Base(Type type) {
        World world = new World(BOARD_SIZE_X, BOARD_SIZE_Y, type);
        Vector2D[] possiblePositions = generatePossiblePositions(world.getHeight(), world.getWidth());

        world.addOrganism(new Wolf(getRandomPosition(possiblePositions)));
        world.addOrganism(new Wolf(getRandomPosition(possiblePositions)));
        world.addOrganism(new Sheep(getRandomPosition(possiblePositions)));
        world.addOrganism(new Sheep(getRandomPosition(possiblePositions)));
        world.addOrganism(new Dandelion(getRandomPosition(possiblePositions)));
        world.addOrganism(new Fox(getRandomPosition(possiblePositions)));
        world.addOrganism(new Turtle(getRandomPosition(possiblePositions)));
        world.addOrganism(new Grass(getRandomPosition(possiblePositions)));
        world.addOrganism(new Antelope(getRandomPosition(possiblePositions)));
        world.addOrganism(new Guarana(getRandomPosition(possiblePositions)));
        world.addOrganism(new Guarana(getRandomPosition(possiblePositions)));
        world.addOrganism(new Belladonna(getRandomPosition(possiblePositions)));
        world.addOrganism(new Belladonna(getRandomPosition(possiblePositions)));
        world.addOrganism(new Hogweed(getRandomPosition(possiblePositions)));
        world.addOrganism(new Human(getRandomPosition(possiblePositions)));

        return world;
    }

    private static Vector2D[] generatePossiblePositions(int height, int width) {
        Vector2D[] positions = new Vector2D[height * width];
        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                positions[index++] = new Vector2D(y, x);
            }
        }

        return positions;
    }

    private static Vector2D getRandomPosition(Vector2D[] positions) {
        int index = (int) (Math.random() * positions.length);
        Vector2D position = positions[index];
        positions[index] = positions[positions.length - 1];
        positions = Arrays.copyOf(positions, positions.length - 1);
        return position;
    }


    private final int height;
    private final int width;
    private int roundNumber;
    private Move move = Move.STOP;
    private Type type = Type.cartesian;
    private NotificationManager notificationManager;
    Vector<Organism> organisms;

    private void moveOrganisms(){
        organisms.sort((Organism o1, Organism o2) -> {
            if(o1.getInitiative() == o2.getInitiative()){
                return o2.getAge() - o1.getAge();
            }
            return o2.getInitiative() - o1.getInitiative() ;
        });

        for(int i = 0; i < organisms.size(); i++){
            Organism organism = organisms.get(i);

            if(organism.isAlive() ){
                organism.action();
                organism.collision();
            }
            organism.incrementAge();
        }
    }

    private void getRidOfDeadOrganisms(){
        for(int i = 0; i < organisms.size(); i++){
            if(!organisms.get(i).isAlive()){
                organisms.remove(i);
                getRidOfDeadOrganisms();
                break;
            }
        }
    }

    private void announceNewRound(){
        notificationManager.clear();
        for(Organism organism : organisms){
            organism.newRound();
        }
    }
}
