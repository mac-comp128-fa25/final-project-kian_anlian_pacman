import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.events.KeyboardEvent;

/*
 * This class handles taking in inputs for keys. We use booleans to represent whether a key is currently pressed or not. 
 * We have methods for each key to change state on keyPress/keyRelease. Credit to 
 * RyiSnow on YouTube for the concept.
 */
public class KeyHandler { 
    private boolean upPressed, leftPressed, rightPressed, downPressed;
    private GameCharacter gameCharacter;
    private Key pressedKey, releasedKey;

    /*
     * One example for purpose of GameCharacter interface. 
     * We could swap in other characters and as long as
     * they implement GameCharacter, we can move them
     * using KeyHandler.
     */
    public KeyHandler (GameCharacter gameCharacter){ 
        this.gameCharacter = gameCharacter;
    }

    public void checkKeyPresses(){
        if (upPressed){
            gameCharacter.moveUp();
        }

        if (downPressed){
            gameCharacter.moveDown();
        }

        if (leftPressed){
            gameCharacter.moveLeft();
        }

        if (rightPressed){
            gameCharacter.moveRight();
        }
    }

    /*
     * Both of the following methods will be used in the run method lambda,
     * called in canvas.onKeyDown() and canvas.onKeyUp() respectively.
     */
    public void keyPressed(KeyboardEvent event){ 
        pressedKey = event.getKey();

        if (pressedKey == Key.UP_ARROW){
            upPressed = true;
        }

        if (pressedKey == Key.DOWN_ARROW){
            downPressed = true;
        }

        if (pressedKey == Key.LEFT_ARROW){
            leftPressed = true;
        }

        if (pressedKey == Key.RIGHT_ARROW){
            rightPressed = true;
        }
    }

    public void keyReleased(KeyboardEvent event){
        releasedKey = event.getKey();

        if (releasedKey == Key.UP_ARROW){
            upPressed = false;
        }

        if (releasedKey == Key.DOWN_ARROW){
            downPressed = false;
        }

        if (releasedKey == Key.LEFT_ARROW){
            leftPressed = false;
        }

        if (releasedKey == Key.RIGHT_ARROW){
            rightPressed = false;
        }
    }
}
