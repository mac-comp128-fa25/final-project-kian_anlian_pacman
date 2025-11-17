import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

public class MazeWall implements GameObject{
    
    private GraphicsObject wallShape;
    private Vector2D positionVector;

    public MazeWall(Vector2D positionVector){
        this.positionVector = positionVector;
        wallShape = new Rectangle(positionVector.getVX(), positionVector.getVY(), 10,10);
    }

    @Override
    public GraphicsObject getObjectShape() {
        return wallShape; //Might need the GraphicsObject reference in MazeManager
    }

    @Override
    public void addToCanvas() {
    }

    @Override
    public void removeFromCanvas() {
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
