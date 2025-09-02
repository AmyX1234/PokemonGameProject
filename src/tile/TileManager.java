package tile;

import entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamepanel;
    public Tile[] tile; // Array to store different tile types
    public int[][] mapTileNum; // 2D array to store tile numbers for the map

    // Constructor
    public TileManager(GamePanel gp) {
        gamepanel = gp;
        tile = new Tile[10]; // Initialize tile array with size 10
        mapTileNum = new int[gamepanel.maxWorldCol][gamepanel.maxWorldRow]; // Initialize mapTileNum array with dimensions from GamePanel
        getTileImage(); // Load tile images
        loadMap("/Map/map.02"); // Load map data
    }

    // Method to load map data from a file
    public void loadMap(String mapfile) {
        try (InputStream is = getClass().getResourceAsStream(mapfile);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            int col = 0;
            int row = 0;

            // Read map data line by line
            while (col < gamepanel.maxWorldCol && row < gamepanel.maxWorldRow) {
                String line = br.readLine();

                // Parse each line to get tile numbers
                while (col < gamepanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                // Move to the next row when finished with a line
                if (col == gamepanel.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        } catch (Exception e) {
              e.printStackTrace();
        }
    }

    // Method to load tile images
    public void getTileImage() {
        try {
            String title = "/tile/";
            // Load each tile image and set properties
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream(title + "grass.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream(title + "water.png"));
            tile[1].fight = true;
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream(title + "wall.png"));
            tile[2].collision = true; // Mark tile with collision
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream(title + "bushGrass.png"));
            tile[3].fight = true;
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream(title + "tree.png"));
            tile[4].collision = true; // Mark tile with collision
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream(title + "sand.png"));
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream(title + "earth.png"));
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream(title + "fire.png"));
            tile[7].fight = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to draw tiles on the screen
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        // Loop through mapTileNum array
        while (worldCol < gamepanel.maxWorldCol && worldRow < gamepanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gamepanel.tileSize;
            int worldY = worldRow * gamepanel.tileSize;
            int screenX = worldX - gamepanel.player.worldX + gamepanel.player.screenX; // Calculate screen position
            int screenY = worldY - gamepanel.player.worldY + gamepanel.player.screenY;
            // Draw only visible tiles
            if ( (worldX + gamepanel.tileSize) > gamepanel.player.worldX - gamepanel.player.screenX &&
                    (worldX - gamepanel.tileSize) < gamepanel.player.worldX + gamepanel.player.screenX &&
                    (worldY + gamepanel.tileSize) > gamepanel.player.worldY - gamepanel.player.screenY &&
                    (worldY - gamepanel.tileSize) < gamepanel.player.worldY + gamepanel.player.screenY
            )
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gamepanel.tileSize, gamepanel.tileSize, null);

            }
            worldCol++;
            if (worldCol == gamepanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
