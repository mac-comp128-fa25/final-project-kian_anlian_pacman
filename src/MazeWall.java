import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

public class MazeWall implements GameObject{
    private GraphicsObject wallShape;

    @Override
    public void createObject() {
        wallShape = new Rectangle(10,10,10,10); //temp
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
    public double getCenterX() {
        return 0; //temp
    }

    @Override
    public double getCenterY() {
        return 0; //temp
    }

}
