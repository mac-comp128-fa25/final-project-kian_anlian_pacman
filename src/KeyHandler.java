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

    public void keyPressed(KeyboardEvent event){ 
        Key pressedKey = event.getKey();

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
}
