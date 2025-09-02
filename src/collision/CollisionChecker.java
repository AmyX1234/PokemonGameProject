package collision;
import attackPhrase.Battle;
import entity.Entity;
import main.GamePanel;
import java.util.Random;

public class CollisionChecker{

    GamePanel gamepanel;
    public static boolean battleOpen = false;

    // Constructor
    public CollisionChecker(GamePanel gp){
        gamepanel = gp;
    }

    // Method to check collision with tiles for the given entity
    public void checkTile(Entity entity){
        // Calculate entity's boundaries
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // Calculate the tile positions of the entity's boundaries
        int entityLeftCol = entityLeftWorldX / gamepanel.tileSize; // Column of the map the entity's left side is in
        int entityRightCol = entityRightWorldX / gamepanel.tileSize;
        int entityTopRow = entityTopWorldY / gamepanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamepanel.tileSize;

        int tileNum1, tileNum2;
        // Check collision based on entity's direction
        if (entity.direction.equals("up")){
            // Adjust top row position based on entity's speed
            entityTopRow = (entityTopWorldY - entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityTopRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].fight || gamepanel.tileM.tile[tileNum2].fight) {
                Random random = new Random();

                int randomNumber = random.nextInt(30); // Generates a random number between 0 and 30
                if (randomNumber == 3 && !battleOpen) {
                    Battle.battleWindow(); // Trigger battle window
                    entity.direction = "up";
                    battleOpen = true;
                }
            }

        }
        else if (entity.direction.equals("down")){
            // Adjust bottom row position based on entity's speed
            entityBottomRow = (entityBottomWorldY + entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].fight || gamepanel.tileM.tile[tileNum2].fight) {
                Random random = new Random();

                int randomNumber = random.nextInt(30); // Generates a random number between 0 and 30
                if (randomNumber == 3 && !battleOpen) {
                    System.out.println("down is working start");
                    Battle.battleWindow(); // Trigger battle window
                    entity.direction = "down";
                    battleOpen = true;
                }
            }
        }
        else if (entity.direction.equals("left")){
            // Adjust left column position based on entity's speed
            entityLeftCol = (entityLeftWorldX - entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].fight || gamepanel.tileM.tile[tileNum2].fight) {
                Random random = new Random();

                int randomNumber = random.nextInt(30); // Generates a random number between 0 and 30
                if (randomNumber == 3 && !battleOpen) {
                    Battle.battleWindow(); // Trigger battle window
                    entity.direction = "left";
                    battleOpen = true;
                }
            }
        }
        else if (entity.direction.equals("right")){
            // Adjust right column position based on entity's speed
            entityRightCol = (entityRightWorldX + entity.speed) / gamepanel.tileSize;
            // Get tile numbers at the adjusted positions
            tileNum1 = gamepanel.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gamepanel.tileM.mapTileNum[entityRightCol][entityBottomRow];
            // Check for collision and trigger battle event if necessary
            if (gamepanel.tileM.tile[tileNum1].collision || gamepanel.tileM.tile[tileNum2].collision){
                entity.collisionOn = true;
            }
            if (gamepanel.tileM.tile[tileNum1].fight || gamepanel.tileM.tile[tileNum2].fight) {
                Random random = new Random();

                int randomNumber = random.nextInt(30); // Generates a random number between 0 and 30
                if (randomNumber == 3 && !battleOpen) {
                    Battle.battleWindow(); // Trigger battle window
                    entity.direction = "right";
                    battleOpen = true;
                }
            }
        }
    }
}
