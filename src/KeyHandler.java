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
    private Key pressedKey, releasedKey;

    public KeyHandler (Movement movement, GameObject gameObject, TileManager tileManager){ 
        this.movement = movement;
        this.gameObject = gameObject;
        this.tileManager = tileManager;
    }

    public void checkKeyPresses(){ //TODO: Get help from Suhas on walkable-tile logic
        if (upPressed && (tileManager.getCurrentTile(gameObject).isLegal() || !tileManager.getAboveTile(gameObject).isLegal())){
            movement.moveUp();
        }

        if (downPressed && (tileManager.getCurrentTile(gameObject).isLegal() || !tileManager.getBelowTile(gameObject).isLegal())){
            movement.moveDown();
        }

        if (leftPressed && (tileManager.getCurrentTile(gameObject).isLegal() || tileManager.getLeftTile(gameObject).isLegal())){
            movement.moveLeft();
        }

        if (rightPressed && (tileManager.getCurrentTile(gameObject).isLegal() || tileManager.getRightTile(gameObject).isLegal())){
            movement.moveRight();
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

    public void keyReleased(KeyboardEvent event) {
        releasedKey = event.getKey();
        if (releasedKey == Key.UP_ARROW){
            upPressed = false;
        }
        if (releasedKey == Key.LEFT_ARROW){
            leftPressed = false;
        }
        if (releasedKey == Key.RIGHT_ARROW){
            rightPressed = false;
        }
        if (releasedKey == Key.DOWN_ARROW){
            downPressed = false;
        }  
    }
}
