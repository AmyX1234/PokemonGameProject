package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create a window for the game
        JFrame window = new JFrame();

        // Create a menu for the game
        JFrame menu = new ImageAnimationGUI();

        // Ensure the menu stays on top of other windows
        menu.setAlwaysOnTop(true);

        // Set the behavior for closing the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent resizing of the window
        window.setResizable(false);

        // Set the title of the window
        window.setTitle("2D Adventure");

        // Create a panel for the game
        GamePanel gamePanel = new GamePanel();

        // Add the game panel to the window
        window.add(gamePanel);

        // Size the window to fit the game panel
        window.pack();

        // Center the window on the screen
        window.setLocationRelativeTo(null);

        // Make the window visible
        window.setVisible(true);

        // Start the game thread
        gamePanel.startGameThread();
    }
}