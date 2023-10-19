package GUI;

import Simulation.Vector2D;
import Simulation.NotificationManager;
import Simulation.World;
import Simulation.Organisms.Organism;
import Simulation.Organisms.Plants.*;
import Simulation.Organisms.Animals.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Visualization extends JPanel implements MouseListener, KeyListener {
    public Visualization(int windowHeight, World world) {
        this.height = world.getHeight();
        this.width = world.getWidth();
        this.windowHeight = windowHeight;
        this.world = world;
        this.newPosition = new Vector2D(0,0);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        initializePopupMenu();
    }

    private void initializePopupMenu(){
        newOrganismMenu = new JPopupMenu();
        Vector2D position = new Vector2D(0,0);
        Organism[] organisms = {
                new Wolf(position),
                new Sheep(position),
                new Fox(position),
                new Turtle(position),
                new Antelope(position),
                new Grass(position),
                new Dandelion(position),
                new Guarana(position),
                new Belladonna(position),
                new Hogweed(position)
        };

        for(Organism organism : organisms){
            JMenuItem organismMenu = new JMenuItem(organism.toString());
            organismMenu.setBackground(organism.draw());
            organismMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    putOrganism(organism);
                }
            });

            newOrganismMenu.add(organismMenu);
        }
    }

    public NotificationManager getNotificationsManager(){
        return world.getNotificationManager();
    }

    public void setWorld(World world) {
        this.world = world;
        this.width = world.getWidth();
        this.height = world.getHeight();

        paint(this.getGraphics());
    }

    public void nextRound(){
        world.nextRound();
        paint(this.getGraphics());
        System.out.print(world.getNotificationManager().printOut());
        requestFocus();
    }

    public boolean hasHuman(){
        for(Organism organism : world.getOrganisms()){
            if(organism instanceof Human) return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g){
        animalSize = windowHeight / height;
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0,0, width * animalSize, height * animalSize);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Organism organism = world.getOrganismAtPosition(new Vector2D(y,x));

                if(organism != null){
                    g.setColor(organism.draw());

                    if(world.getType() == World.Type.cartesian){
                        g.fillRect(x* animalSize,y* animalSize, animalSize, animalSize);
                    }
                    else {
                        int[] xPoints = new int[6];
                        int[] yPoints = new int[6];
                        double xtemp = x;

                        if(y %2 == 0){
                            xtemp = x + 0.5;
                        }

                        for (int i = 0; i < 6; i++) {
                            int xval = (int) (xtemp * animalSize + animalSize /2
                                    * Math.sin(i * 2 * Math.PI / 6D));
                            int yval = (int) (y * animalSize + animalSize /2
                                    * Math.cos(i * 2 * Math.PI / 6D));

                            xPoints[i] = xval;
                            yPoints[i] = yval;
                        }
                        g.fillPolygon(xPoints, yPoints, yPoints.length);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();

        newPosition = new Vector2D(y/ animalSize,x/ animalSize);
        newOrganismMenu.show(this,x,y);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_UP:
                System.out.println("move UP");
                world.setMove(World.Move.UP);
                break;

            case KeyEvent.VK_DOWN:
                System.out.println("move DOWN");
                world.setMove(World.Move.DOWN);
                break;

            case KeyEvent.VK_LEFT:
                System.out.println("move LEFT");
                world.setMove(World.Move.LEFT);
                break;

            case KeyEvent.VK_RIGHT:
                System.out.println("move RIGHT");
                world.setMove(World.Move.RIGHT);
                break;

            case KeyEvent.VK_R:
                System.out.println("R");
                world.setMove(World.Move.SPECIAL_ABILITY);
                break;
        }
        paint(getGraphics());
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    public World getWorld() {
        return world;
    }

    private static final Color BACKGROUND_COLOR = new Color(0,0,0);
    private JPopupMenu newOrganismMenu;
    private World world;
    private int height;
    private int width;
    private Vector2D newPosition;
    private final int windowHeight;
    private int animalSize;

    private void putOrganism(Organism organism){
        Organism collidingOrganism = world.getOrganismAtPosition(newPosition);

        while(collidingOrganism != null){
            collidingOrganism.kill();
            collidingOrganism = world.getOrganismAtPosition(newPosition);
        }

        organism.setPosition(newPosition);
        world.addOrganism(organism.copy());
        paint(getGraphics());
    }
}