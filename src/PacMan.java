import java.awt.Color;
import edu.macalester.graphics.Arc;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class PacMan implements GameCharacter, GameObject{
    private Vector2D positionVector;
    private Vector2D velocityVector = new Vector2D(0,0); //Have to instantiate otherwise null
    private CanvasWindow canvas;
    private Arc pacManShape;
    private boolean facingLeft, facingRight, facingUp, facingDown;
    
    public PacMan (Vector2D positionVector, CanvasWindow canvas){//Spawn him in the middle
        this.positionVector = positionVector;
        this.canvas = canvas;
        pacManShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,5, 60, 240); //-45 to 45 deg should give us the right look
        pacManShape.setStrokeColor(Color.YELLOW);
        pacManShape.setStrokeWidth(5);
        pacManShape.setScale(5);
        addToCanvas();
    }

    @Override
    public void handleCollisions() {//Handle collisions w/ MazeWalls and Ghosts
    }

    @Override
    public void moveUp() {
        velocityVector.set(0,2.5);
        positionVector.add(velocityVector.getVX(),velocityVector.getVY()); //move 0 units right and 5 units up every frame
        pacManShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingUp){
            pacManShape.setRotation(-90);
            facingUp = true;
            facingDown = false;
            facingLeft = false;
            facingRight = false;
        }
    }

    @Override
    public void moveDown() {
        velocityVector.set(0,2.5);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); //move 0 units right and 5 units down every frame
        pacManShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingDown){
            pacManShape.setRotation(90);
            facingDown = true;
            facingUp = false;
            facingLeft = false;
            facingRight = false;
        }
    }

    @Override
    public void moveLeft() {
        velocityVector.set(2.5,0);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); 
        pacManShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingLeft){
            pacManShape.setRotation(180);
            facingLeft = true;
            facingUp = false;
            facingRight = false;
            facingDown = false;
        }
    }

    @Override
    public void moveRight() {
        velocityVector.set(2.5,0);
        positionVector.add(velocityVector.getVX(), velocityVector.getVY());
        pacManShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingRight){
            pacManShape.setRotation(0);
            facingRight = true;
            facingLeft = false;
            facingDown = false;
            facingUp = false;
        }
    }
    
    @Override
    public GraphicsObject getObjectShape() {
        return pacManShape;
    }

    @Override
    public void addToCanvas() {
        canvas.add(pacManShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(pacManShape);
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
      return positionVector.getVY();
    }

}
