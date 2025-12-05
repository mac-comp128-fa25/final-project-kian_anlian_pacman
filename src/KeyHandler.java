import java.awt.Color;
import java.util.List;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;

/*
 * This class handles taking in inputs for keys. We use booleans to represent whether a key is currently pressed or not. 
 * We have methods for each key to change state on keyPress/keyRelease. Credit to 
 * RyiSnow on YouTube for the concept.
 */
public class KeyHandler { 
    private boolean upPressed, leftPressed, rightPressed, downPressed;
    private Movement movement;
    private GameObject gameObject;
    private TileManager tileManager;
    private Key pressedKey;

    public KeyHandler (Movement movement, GameObject gameObject, TileManager tileManager){ 
        this.movement = movement;
        this.gameObject = gameObject;
        this.tileManager = tileManager;
    }
    
    public void checkKeyPresses(){ 
        if (!movement.hitCircleTopCollision() && upPressed){
            movement.moveUp();
        }
                                                                                                                                                            
        if (!movement.hitCircleBottomCollision() && downPressed){ 
            movement.moveDown();
        }

        if (!movement.hitCircleLeftCollision() && leftPressed){
            movement.moveLeft();
        }

        if (!movement.hitCircleRightCollision() && rightPressed){
            movement.moveRight();
        }
        visualizePaths();
    }

    /*
     * Visual way during gameplay to make sure tiles being set legal/illegal correctly and paths being created correctly.
     */
    public void visualizePaths(){ 
        tileManager.getCurrentTile(gameObject).colorTile(Color.GREEN);
        
        List<Tile>  tileList = tileManager.getAdjacentTiles(tileManager.getCurrentTile(gameObject));
        
        for (Tile tile : tileList){
            tile.colorTile(Color.GREEN);
        }
    }

    public void keyPressed(KeyboardEvent event){ 
        pressedKey = event.getKey();

        if (pressedKey == Key.UP_ARROW){
            upPressed = true;
            leftPressed = false;
            rightPressed = false;
            downPressed = false;
        }

        if (pressedKey == Key.DOWN_ARROW){
            downPressed = true;
            upPressed = false;
            rightPressed = false;
            leftPressed = false;
        }

        if (pressedKey == Key.LEFT_ARROW){
            leftPressed = true;
            rightPressed = false;
            upPressed = false;
            downPressed = false;
        }

        if (pressedKey == Key.RIGHT_ARROW){
            rightPressed = true;
            upPressed = false;
            leftPressed = false;
            downPressed = false;
        }
    }

    public void enqueueDirection(){ //TODO: Implement enqueing key presses and centering in tile
        /*
         * Broad algorithm: Store each key press as a request to switch to that direction. 
         * If the direction is on the same axis (i.e going from right to left) 
         *   Execute then mark that request false: (Call movement.Up() then set upRequested = false)
         * Otherwise: 
         *  If !movement.(hitCircleCollisionMethod) && requested
         *      Execute and mark that request false 
         *  Else
         *      Do nothing.
         */
    }
}
