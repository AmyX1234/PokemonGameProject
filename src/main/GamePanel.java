package main;

import javax.swing.*;
import java.awt.*;

import collision.CollisionChecker;
import tile.TileManager;
import entity.Player;

public class GamePanel extends JPanel implements Runnable { // Subclass of JPanel and implements the Runnable interface for multi-threading

    // Screen Settings
    final int originalTileSize = 16; // Size of original tile
    final int scale = 3; // Scaling factor for the image of the character

    // Calculating scaled tileSize
    public final int tileSize = originalTileSize * scale; // Scaled tileSize (48x48)
    public final int maxScreenCol = 16; // Maximum number of columns on the screen
    public final int maxScreenRow = 12; // Maximum number of rows on the screen
    public final int screenWidth = tileSize * maxScreenCol; // Total screen width (768 pixels)
    public final int screenHeight = tileSize * maxScreenRow; // Total screen height (576 pixels)
    public final int maxWorldCol = 50; // Maximum number of columns in the world
    public final int maxWorldRow = 50; // Maximum number of rows in the world
    public final int maxWorldWidth = tileSize * maxWorldCol; // Total world width
    public final int maxWorldHeight = tileSize * maxWorldRow; // Total world height

    // Frames Per Second (FPS)
    int FPS = 60;

    public TileManager tileM = new TileManager(this); // Tile manager instance
    KeyHandler keyH = new KeyHandler(); // Key handler instance
    Thread gameThread; // Thread for game loop
    public CollisionChecker cChecker = new CollisionChecker(this); // Collision checker instance
    public Player player = new Player(this, keyH, "Bulbasaur"); // Player instance

    // Constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Setting preferred panel size
        this.setBackground(Color.BLACK); // Setting background color
        this.setDoubleBuffered(true); // Enabling double buffering for smoother graphics
        this.addKeyListener(keyH); // Adding key listener for user input
        this.setFocusable(true); // Making panel focusable for key input
    }

    // Method to start the game thread
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Game loop
    public void run() {
        double drawInterval = (double) 1000000000 / FPS; // Interval between each frame for desired FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update(); // Update game state
                repaint(); // Repaint the screen
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount); // Output current FPS
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // Update method to update game state
    public void update() {
        player.update(); // Update player state
    }

    // Method to paint graphics on the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // Draw tiles
        player.draw(g2); // Draw player

        g2.dispose(); // Dispose graphics object
    }

}
