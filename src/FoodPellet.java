import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;

public class FoodPellet implements GameObject{
    private Ellipse pelletShape;
    private Vector2D positionVector;
    
    /*
     * TODO: Use a HashMap where the key is some tile at (x,y) and the value is 
     * the pelletShape.
     */

    public FoodPellet(Vector2D positionVector){
        this.positionVector = positionVector;
        pelletShape = new Ellipse(0,0,10,10); //temp
    }

    @Override
    public GraphicsObject getObjectShape() {
        return pelletShape;
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
