import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;

/**
 * @author Kian Naeimi
 * December 2025
 * 
 * This class allows us smooth transitions between key input events! Without a dedicated KeyHandler class that 
 * manipulates boolean values for each key state, movement becomes VERY choppy and delayed. Player's can 
 * move Pac-Man (or any other GraphicsObject!) with either WASD controls or the arrow keys. Similarly to
 * the GhostManager's method of managing movement state, key-presses only send requests to our Movement 
 * class. Pac-Man uses a sub-class of StandardMovement: RotationMovement! All it adds is adjusting the
 * orientation of the GraphicsObject relative to it's current direction. Both methods are called in
 * the main update loop. 
 * 
 * Credit to RyiSnow on YouTube for the general structure of the class: https://www.youtube.com/watch?v=VpH33Uw-_0E&t=688s
 */

 
public class KeyHandler { 
    private boolean upPressed, leftPressed, rightPressed, downPressed, pausePressed;
    private Movement movement;
    private Key pressedKey;

    public KeyHandler (Movement movement, GraphicsObject objectShape, TileManager tileManager){ 
        this.movement = movement;
    }
    
    /**
     * Delegates movement requests to objectShape's Movement implementation.
     */
    public void checkKeyPresses(){ 
        if (upPressed) movement.queueUp();
        if (downPressed) movement.queueDown();
        if (leftPressed) movement.queueLeft();
        if (rightPressed)  movement.queueRight();
        if (pausePressed) PacManGame.pauseGame();
    }

    /**
     * Nothing interesting here besides what we DON'T have. 
     * Usually in a KeyHandler you would also have a KeyReleased()
     * method that creates some behavior onKeyUp. But here, 
     * we actually can just simply maintain the last key-press
     * until the next input by forgoing KeyReleased() and just
     * setting every other keyPressed state to false.
     * This allows us to create that snappy movement behavior
     * where user's can quickly chain together movement requests
     * without worrying about keeping keys held down.
     * 
     * @param event User's inputted key.
     */
    public void keyPressed(KeyboardEvent event){ 
        pressedKey = event.getKey();

        if (pressedKey == Key.UP_ARROW || pressedKey == Key.W){
            upPressed = true;
            leftPressed = false;
            rightPressed = false;
            downPressed = false;
        }

        if (pressedKey == Key.DOWN_ARROW || pressedKey == Key.S){
            downPressed = true;
            upPressed = false;
            rightPressed = false;
            leftPressed = false;
        }

        if (pressedKey == Key.LEFT_ARROW || pressedKey == Key.A){
            leftPressed = true;
            rightPressed = false;
            upPressed = false;
            downPressed = false;
        }

        if (pressedKey == Key.RIGHT_ARROW || pressedKey == Key.D){
            rightPressed = true;
            upPressed = false;
            leftPressed = false;
            downPressed = false;
        }

        if (pressedKey == Key.ESCAPE || pressedKey == Key.P){
            pausePressed = true;
        }

        if (pressedKey != Key.ESCAPE && pressedKey != Key.P){
            pausePressed = false;
            PacManGame.gameRunning();
        }
    }
}
