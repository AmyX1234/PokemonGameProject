package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    // Entity's position in the world
    public int worldX, worldY;

    // Entity's speed and direction of movement
    public int speed;
    public String direction;

    // Flags for collision and battle window
    public boolean collisionOn = false;

    // Images for entity's movement sprites
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    // Sprite animation variables
    public int spriteCounter = 0; // Counter for sprite animation
    public int spriteNum = 1; // Number of sprites for animation
    public Rectangle solidArea; // Area representing entity's collision bounds
}
