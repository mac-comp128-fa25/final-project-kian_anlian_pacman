import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;

public class Ghost implements GameCharacter, GameObject{
    private Vector2D positionVector;
    private Ellipse ghostShape; //ellipse until we figure out how to draw ghosts
    private CanvasWindow canvas;

    public Ghost (Vector2D positionVector, CanvasWindow canvas){
        this.positionVector = positionVector;
        this.canvas = canvas;
        ghostShape = new Ellipse(0,0,10,10); //temp
    }
    @Override
    public void handleCollisions() {
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
        return ghostShape;
    }

    @Override
    public void addToCanvas() {
        canvas.add(ghostShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(ghostShape);
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
