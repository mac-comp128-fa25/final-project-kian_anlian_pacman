import edu.macalester.graphics.CanvasWindow;

/**
 * @author Kian Naeimi
 * December 2025
 * 
 * Provides GraphicsObject rotation for Pac-Man based on current direction.
 */
public class RotationMovement extends StandardMovement{
    private boolean facingLeft, facingRight, facingUp, facingDown;
    
    public RotationMovement(Vector2D positionVector, CanvasWindow canvas){
        super(positionVector, canvas);
        setSpeed(3); //Good speed for challenging but fair gameplay
    }

    @Override
    public void moveUp() {
        super.moveUp();
        
        if (!facingUp){
            getShape().setRotation(-90);
            facingUp = true;
            facingDown = false;
            facingLeft = false;
            facingRight = false;
        }
    }

    @Override
    public void moveDown() {
        super.moveDown();
        
        if (!facingDown){
            getShape().setRotation(90);
            facingDown = true;
            facingUp = false;
            facingLeft = false;
            facingRight = false;
        }
    }

    @Override
    public void moveLeft() {
       super.moveLeft();

       if (!facingLeft){
            getShape().setRotation(180);
            facingLeft = true;
            facingUp = false;
            facingRight = false;
            facingDown = false;
        }
    }

    @Override
    public void moveRight() {
        super.moveRight();

          if (!facingRight){
            getShape().setRotation(0);
            facingRight = true;
            facingLeft = false;
            facingDown = false;
            facingUp = false;
        }
    }
}
