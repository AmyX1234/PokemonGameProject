package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity { // Player class extending Entity

    // Instance variables
    GamePanel gp;
    KeyHandler keyH;
    public static String selectedPokemon = "Bulbasaur";
    int Pokemon;

    // Player screen position
    public final int screenX;
    public final int screenY;

    // Constructor
    public Player(GamePanel gp, KeyHandler keyH, String choice) {
        this.gp = gp;
        this.keyH = keyH;

        // Calculate screen position
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Set solid area for collision
        solidArea = new Rectangle();
        solidArea.x = 8; // Hitbox in the middle of player's boundaries
        solidArea.y = 16; // Hitbox at the lower end of player's boundaries
        solidArea.width = 32; // Less than the tile size to make sure the player can move easily
        solidArea.height = 32;

        // Set default values
        setDefaultValues();
        // Get player image based on choice
        getPlayerImage(choice);
    }

    // Method to set default values
    public void setDefaultValues() {
        worldX = gp.tileSize * 6;
        worldY = gp.tileSize * 46;
        speed = 4;
        direction = "down"; // Default direction
    }

    // Method to get player image based on choice
    public void getPlayerImage(String choice) {
        String basePath = "/Player/";
//        String pokemon = switch (choice) {
//            case "Bulbasaur" -> "Bulbasaur";
//            case "Charmander" -> "Charmander";
//            case "Squirtle" -> "Squirtle";
//            default ->
//                // Default to Charmander if no valid choice is made
//                    "Charmander";
//        };

        try {
            // Load player images for different directions
            up1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_up1_16x16.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_up2_16x16.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_down1_16x16.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_down2_16x16.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_left1_16x16.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_left2_16x16.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_right1_16x16.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream(basePath + choice + "_right2_16x16.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update player state
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Move player if no collision
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        if (worldY - speed >= 0) {
                            worldY -= speed;
                        }
                        break;
                    case "down":
                        if (worldY + gp.tileSize + speed <= gp.maxWorldHeight * gp.tileSize) {
                            worldY += speed;
                        }
                        break;
                    case "left":
                        if (worldX - speed >= 0) {
                            worldX -= speed;
                        }
                        break;
                    case "right":
                        if (worldX + gp.tileSize + speed <= gp.maxWorldWidth * gp.tileSize) {
                            worldX += speed;
                        }
                        break;
                }
            }

            // Update sprite animation
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // Check if switch character button is pressed
        if (keyH.switchCharacterPressed) {
            // Show dialog to switch character
            String[] options = {"Bulbasaur", "Charmander", "Squirtle"};
            int choiceIndex = JOptionPane.showOptionDialog(
                    null,
                    "Which Pokemon would you like to switch to?",
                    "Switch Pokemon",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    JOptionPane.CLOSED_OPTION // Set CLOSE_OPTION as default
            );
            // Handle player's choice
            if (choiceIndex == -1) {
                choiceIndex = Pokemon;
            }
            selectedPokemon = options[choiceIndex];
            getPlayerImage(selectedPokemon);
            keyH.switchCharacterPressed = false;
            Pokemon = choiceIndex;
        }
    }

    // Method to draw player
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Set image based on direction and sprite number
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        // Draw player image
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
