package Simulation;

import Simulation.Organisms.Organism;
import Simulation.Organisms.Animals.*;
import Simulation.Organisms.Plants.*;
import javax.swing.*;
import java.io.*;

public class FilesManager {
    public void save(World world, File file){
        try {
            FileWriter out = new FileWriter(file);
            String typeStr = world.getType().equals(World.Type.hexagonal) ? "HEX" : "CART";
            out.write(world.getRoundNumber() + " " + world.getHeight() + " " + world.getWidth()  + " " + typeStr+ "\n");

            for(Organism organism : world.getOrganisms()){
                out.write(organism.toString() + " " +
                        organism.getAge() + " " +
                        organism.getPosition().getY() + " " +
                        organism.getPosition().getX());

                if(organism instanceof Human){
                    out.write(" " + ((Human) organism).getSpecialAbilityCooldown());
                }
                out.write("\n");
            }
            out.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Couldn't save the file","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public World load(File file){
        try {
            FileInputStream fs = new FileInputStream(file);
            DataInputStream ds = new DataInputStream(fs);
            BufferedReader in = new BufferedReader(new InputStreamReader(ds));

            String[] RoundHeightWidthType = in.readLine().split(" ");
            World.Type type = World.Type.cartesian;

            if(RoundHeightWidthType[3].equals("HEX")){
                type = World.Type.hexagonal;
            }

            World world = new World(Integer.parseInt(RoundHeightWidthType[1]),Integer.parseInt(RoundHeightWidthType[2]), type);
            world.setRoundNumber(Integer.parseInt(RoundHeightWidthType[0]));

            String line;

            while((line = in.readLine()) != null){
                Organism organism = loadOrganism(line);
                if(organism != null){
                    world.addOrganism(organism);
                }
            }

            ds.close();

            return world;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Couldn't load the file","Error",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private Organism loadOrganism(String line) throws Exception {
        String[] attributes = line.split(" ");
        Organism newOrganism = allocateOrganism(attributes[0]);

        if(newOrganism != null){
            newOrganism.setAge(Integer.parseInt(attributes[1]));
            newOrganism.setPosition(new Vector2D(Integer.parseInt(attributes[2]), Integer.parseInt(attributes[3])));

            if(newOrganism instanceof Human){
                ((Human) newOrganism).setSpecialAbilityCooldown(Integer.parseInt(attributes[4]));
            }

            return newOrganism;
        }
        return null;
    }

    private Organism allocateOrganism(String name){
        Vector2D position = new Vector2D(0,0);
        Organism[] organisms = {
                new Human(position),
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
            if(organism.toString().equals(name)){
                return organism.copy();
            }
        }
        return null;
    }
}
