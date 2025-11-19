import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.CanvasWindow;

public class FoodPellet implements GameObject{
    private Ellipse pelletShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private static final int PELLET_SIZE = 4;

    /*
     * TODO: Create a HashMap where the key is some tile at (x,y) and the value is 
     * an object of type FoodPellet.
     */

    public FoodPellet(Vector2D positionVector, CanvasWindow canvas){
        this.positionVector = positionVector;
        this.canvas = canvas;
        pelletShape = new Ellipse(positionVector.getVX(),positionVector.getVY(),PELLET_SIZE,PELLET_SIZE); //temp
        addToCanvas();
    }

    @Override
    public GraphicsObject getObjectShape() {
        return pelletShape;
    }

    @Override
    public void addToCanvas() {
        canvas.add(pelletShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(pelletShape);
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
