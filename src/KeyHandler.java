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
    private Key pressedKey;

    public KeyHandler (Movement movement, GameObject gameObject, TileManager tileManager){ 
        this.movement = movement;
    }
    
    public void checkKeyPresses(){ 
        if (upPressed){
            movement.queueUp();
        }

        if (downPressed){
            movement.queueDown();
        }

        if (leftPressed){
            movement.queueLeft();
        }

        if (rightPressed){
            movement.queueRight();
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

    public void move(){
        movement.handleQueue();
    }
}
