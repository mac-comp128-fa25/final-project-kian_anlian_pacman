import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

// MOST LIKELY DELETE

public class MazeWall implements GameObject{
    
    private GraphicsObject wallShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;

    public MazeWall(Vector2D positionVector, CanvasWindow canvas){
        this.positionVector = positionVector;
        this.canvas = canvas;
        wallShape = new Rectangle(positionVector.getVX(), positionVector.getVY(), 10,10);
    }

    @Override
    public GraphicsObject getObjectShape() {
        return wallShape; //Might need the GraphicsObject reference in MazeManager
    }

    @Override
    public void addToCanvas() {
        canvas.add(wallShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(wallShape);
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
        return positionVector.getVY();
    }

    @Override
    public void handleCollisions() {
    }

}
