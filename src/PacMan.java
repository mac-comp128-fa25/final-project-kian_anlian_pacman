import java.awt.Color;
import edu.macalester.graphics.Arc;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class PacMan implements GameCharacter, GameObject{
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private Arc pacManShape;
    
    public PacMan (Vector2D positionVector, CanvasWindow canvas){//Spawn him in the middle
        this.positionVector = positionVector;
        this.canvas = canvas;
        pacManShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,5, 60, 240); //-45 to 45 deg should give us the right look
        pacManShape.setStrokeColor(Color.YELLOW);
        pacManShape.setStrokeWidth(5);
        pacManShape.setScale(20);
        addToCanvas();
        //TODO: Figure out how to draw the Arc correctly
    }

    @Override
    public void handleCollisions() {//Handle collisions w/ MazeWalls and Ghosts
    }

    @Override
    public void moveUp() {
    }

    @Override
    public void moveDown() {
    }

    @Override
    public void moveLeft() {
    }

    @Override
    public void moveRight() {
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
