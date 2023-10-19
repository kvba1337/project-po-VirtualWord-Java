package GUI;

import Simulation.FilesManager;
import Simulation.World;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Application extends JFrame {
    public static final String TITLE = "Simulation";
    public static final int DEFAULT_HEIGHT = 600;
    public static final int DEFAULT_WIDTH = 500;

    public Application(int height, int width){
        setSize(width,height);
        setMinimumSize(new Dimension(width,height));

        revalidate();

        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        visualization = new Visualization(DEFAULT_HEIGHT * 8/10, World.Base(World.Type.cartesian));
        filesManager = new FilesManager();

        initializeUpperMenu();
        initializeMainPanel();
    }

    public void start(){
        setVisible(true);
    }

    private final Visualization visualization;
    private final FilesManager filesManager;
    private JButton roundButton;
    private JButton notificationsButton;
    private JMenuItem menuItemCartesian;
    private JMenuItem menuItemLoad;
    private JMenuItem menuItemSave;

    private void initializeUpperMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuNew = new JMenu("New Game");
        JMenu menuFile = new JMenu("File");

        initializeUpperMenuButtons();

        menuNew.add(menuItemCartesian);

        menuFile.add(menuItemLoad);
        menuFile.add(menuItemSave);

        menuBar.add(menuNew);
        menuBar.add(menuFile);

        setJMenuBar(menuBar);
    }

    private void initializeUpperMenuButtons(){
        menuItemCartesian = new JMenuItem("Start");
        menuItemLoad = new JMenuItem("Load");
        menuItemSave = new JMenuItem("Save");

        menuItemCartesian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualization.setWorld(World.Base(World.Type.cartesian));
            }
        });

        menuItemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose a file to load");

                int rv = fc.showOpenDialog(null);

                if(rv == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    World world = filesManager.load(file);

                    if(world != null){
                        visualization.setWorld(world);
                    }
                }
            }
        });

        menuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Choose a file to save");

                int rv = fc.showOpenDialog(null);

                if(rv == JFileChooser.APPROVE_OPTION){
                    File file = fc.getSelectedFile();
                    filesManager.save(visualization.getWorld(), file);
                }
            }
        });
    }

    private void initializeMainPanel(){
        initializeButtons();

        JPanel buttonsPanel = new JPanel();

        GridLayout layout = new GridLayout(0,2);
        buttonsPanel.setLayout(layout);

        buttonsPanel.add(roundButton);
        buttonsPanel.add(notificationsButton);

        JSplitPane splitPane = new JSplitPane();

        splitPane.setEnabled(false);
        splitPane.setDividerLocation( DEFAULT_HEIGHT * 8 / 10);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        splitPane.addMouseListener(visualization);

        splitPane.setTopComponent(visualization);
        splitPane.setBottomComponent(buttonsPanel);

        add(splitPane);
    }

    private void initializeButtons(){
        roundButton = new JButton("Next Round");
        notificationsButton = new JButton("Notifications");

        roundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                visualization.nextRound();
            }
        });

        notificationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showConfirmDialog(null, visualization.getNotificationsManager().printOut(),"Notifications", JOptionPane.DEFAULT_OPTION);
            }
        });
    }
}