package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JPanel1 extends JPanel implements ActionListener {
    // Dimensions for the panel and images
    final int PANEL_WIDTH = 1920;
    final int PANEL_HEIGHT = 1080;
    final int IMAGE_WIDTH = 200;  // Desired width for scaled images
    final int IMAGE_HEIGHT = 200; // Desired height for scaled images

    // Timer for changing images
    Timer timer;

    // Initial positions for each character
    int xs = 150;
    int ys = 300;
    int xc = 550;
    int yc = 300;
    int xb = 950;
    int yb = 300;

    // File paths for character images
    private final String[] squirtle = {
            "C:\\Users\\Amy\\IdeaProjects\\Amy World Pokemon Planet\\Source Folder\\Player\\Squirtle_down1_16x16.png",//squirtle_down2
            "C:\\Users\\Amy\\IdeaProjects\\Amy World Pokemon Planet\\Source Folder\\Player\\Squirtle_down2_16x16.png",
    };
    private final String[] charmander = {
            "C:\\Users\\Amy\\IdeaProjects\\Amy World Pokemon Planet\\Source Folder\\Player\\Charmander_down1_16x16.png",
            "C:\\Users\\Amy\\IdeaProjects\\Amy World Pokemon Planet\\Source Folder\\Player\\Charmander_down2_16x16.png",
    };
    private final String[] bubasaur = {
            "C:\\Users\\Amy\\IdeaProjects\\Amy World Pokemon Planet\\Source Folder\\Player\\Bulbasaur_down1_16x16.png",
            "C:\\Users\\Amy\\IdeaProjects\\Amy World Pokemon Planet\\Source Folder\\Player\\Bulbasaur_down2_16x16.png",
    };

    // Arrays to hold images for each character
    private Image[] squirtles;
    private Image[] charmanders;
    private Image[] bulbasaur;

    // Index to track current image being displayed
    private int currentImageIndex = 0;

    // Constructor
    public JPanel1() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // Load images for Squirtle
        squirtles = new Image[squirtle.length];
        for (int i = 0; i < squirtle.length; i++) {
            ImageIcon icon = new ImageIcon(squirtle[i]);
            squirtles[i] = icon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        }

        // Load images for Squirtle
        charmanders = new Image[charmander.length];
        for (int i = 0; i < charmander.length; i++) {
            ImageIcon icon = new ImageIcon(charmander[i]);
            charmanders[i] = icon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        }

        // Load images for Bulbasaur
        bulbasaur = new Image[bubasaur.length];
        for (int i = 0; i < bubasaur.length; i++) {
            ImageIcon icon = new ImageIcon(bubasaur[i]);
            bulbasaur[i] = icon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        }

        // Initialize timer to change images
        timer = new Timer(1000, this); // Change images every 2000 ms (2 seconds)
        timer.start();
    }

    // Method to paint the panel
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(squirtles[currentImageIndex], xs, ys, null); // Draw Squirtle
        g2D.drawImage(charmanders[currentImageIndex], xc, yc, null); // Draw Charmander
        g2D.drawImage(bulbasaur[currentImageIndex], xb, yb, null); // Draw Bulbasaur
    }

    // Action performed when the timer triggers
    @Override
    public void actionPerformed(ActionEvent e) {
        // Increment the index to change to the next image
        currentImageIndex = (currentImageIndex + 1) % squirtles.length;
        currentImageIndex = (currentImageIndex + 1) % charmanders.length;
        currentImageIndex = (currentImageIndex + 1) % bulbasaur.length;
        repaint(); // Repaint the panel to display the new image
    }


}
