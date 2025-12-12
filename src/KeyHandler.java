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
    private HitCircle hitCircle;
    private HitCircle queueHitCircle;

    public KeyHandler (Movement movement, GameObject gameObject, TileManager tileManager){ 
        this.movement = movement;
        hitCircle = movement.getHitCircle();
        queueHitCircle = movement.getQueueHitCircle();
    }
    
    public void checkKeyPresses(){ 
        if (!hitCircle.topTileCollision() && upPressed){  //should just feed request into movement and movement should use hit circle w/larger radius to check if compass point is on a wall
            movement.currentUp();
        }
                                                                                                                                                            
        if (!hitCircle.bottomTileCollision() && downPressed){ 
            movement.currentDown();
        }

        if (!hitCircle.leftTileCollision() && leftPressed){
            movement.currentLeft();
        }

        if (!hitCircle.rightTileCollision() && rightPressed){
            movement.currentRight();
        }

        if (!queueHitCircle.topTileCollision() && upPressed){
            movement.queueUp();
        }

        if (!queueHitCircle.bottomTileCollision() && downPressed){
            movement.queueDown();
        }

        if (!queueHitCircle.leftTileCollision() && leftPressed){
            movement.queueLeft();
        }

        if (!queueHitCircle.rightTileCollision() && rightPressed){
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
