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

    public KeyHandler (Movement movement, GameObject gameObject, TileManager tileManager){ 
        this.movement = movement;
        this.gameObject = gameObject;
        this.tileManager = tileManager;
    }

    public void checkKeyPresses(){ 
        boolean currentLegal = !tileManager.getCurrentTile(gameObject).isWall();
        boolean aboveTileLegal = !tileManager.getAboveTile(gameObject).isWall();
        boolean belowTileLegal = !tileManager.getBelowTile(gameObject).isWall();
        boolean leftTileLegal = !tileManager.getLeftTile(gameObject).isWall();
        boolean rightTileLegal = !tileManager.getRightTile(gameObject).isWall();
        boolean grandBelowTileLegal = !tileManager.getGrandBelowTile(gameObject).isWall(); //Maybe if we have a reference to the grand-child tile we can fix the wall-clipping?
        
        if (upPressed && (currentLegal || aboveTileLegal)){
            movement.moveUp();
        }                                                                                                                                                    
                                                                                                                                                            
        if (downPressed && (currentLegal || belowTileLegal)){ //TODO: FIX COLLISION: Works fine until you hit a wall where the tile on the other side
            movement.moveDown();
        }

        if (leftPressed && (currentLegal || leftTileLegal)){
            movement.moveLeft();
        }

        if (rightPressed && (currentLegal || rightTileLegal)){
            movement.moveRight();
        }

        // debugTiles(); // After visually testing with this method, all tiles are correctly legal/illegal
    }

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
