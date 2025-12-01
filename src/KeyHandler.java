import java.awt.Color;
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
    private static final int COLLISION_BUFFER = 50;

    public KeyHandler (Movement movement, GameObject gameObject, TileManager tileManager){ 
        this.movement = movement;
        this.gameObject = gameObject;
        this.tileManager = tileManager;
    }
    
    public void checkKeyPresses(){ 
        boolean currentLegal = !tileManager.getCurrentTile(gameObject).isWall();
        boolean facingUp = movement.getFacingUp();
        boolean facingDown = movement.getFacingDown();
        boolean facingLeft = movement.getFacingLeft();
        boolean facingRight = movement.getFacingRight();
        
        if (upPressed && (!(facingUp && !currentLegal))){
            movement.moveUp();
        }        
                                                                                                                                                            
        if (downPressed && (!(facingDown && !currentLegal))){ 
            movement.moveDown();
        }

        if (leftPressed && (!(facingLeft && !currentLegal))){
            movement.moveLeft();
        }

        if (rightPressed && (!(facingRight && !currentLegal))){
            movement.moveRight();
        }

        //Buffer so can't clip through walls by spamming opposite directions. If the player taps the direction really quickly a few times before being rubber banded they can still slide... but that's hard to do now.

        if (upPressed &&  facingUp && !currentLegal){
            movement.collisionBuffer(0, COLLISION_BUFFER);
        }

        if (downPressed && facingDown && !currentLegal){
            movement.collisionBuffer(0,-COLLISION_BUFFER);
        }

        if (leftPressed && facingLeft && !facingRight  && !currentLegal){
            movement.collisionBuffer(-COLLISION_BUFFER,0);
        }

        if (rightPressed && facingRight && !facingLeft && !currentLegal){
            movement.collisionBuffer(COLLISION_BUFFER,0);
        }

        // debugTiles();
    }

    /*
     * For debugging tiles being set legal/illegal correctly
     */
    public void debugTiles(){ 
         if (upPressed || downPressed || leftPressed || rightPressed){
            System.out.println(" CURRENT TILE IS LEGAL: " + !tileManager.getCurrentTile(gameObject).isWall() + " COLUMN: " + tileManager.getColumn(gameObject) +" ROW: " + tileManager.getRow(gameObject));
            tileManager.getCurrentTile(gameObject).colorTile(Color.GREEN); 
            tileManager.getAboveTile(gameObject).colorTile(Color.BLUE);
            tileManager.getBelowTile(gameObject).colorTile(Color.RED);
            tileManager.getRightTile(gameObject).colorTile(Color.PINK);
            tileManager.getLeftTile(gameObject).colorTile(Color.ORANGE);
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
