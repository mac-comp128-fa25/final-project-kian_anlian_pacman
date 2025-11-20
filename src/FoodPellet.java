import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class FoodPellet implements GameObject{
    private Ellipse pelletShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private int padding;
    private int sizeScalar;
    
    /*
     * TODO: Create a HashMap where the key is some tile at (x,y) and the value is 
     * an object of type FoodPellet.
     */

    public FoodPellet(Vector2D positionVector, CanvasWindow canvas, Tile tile){
        this.positionVector = positionVector;
        this.canvas = canvas;
        padding = tile.size() / 6;
        sizeScalar = tile.size() / 3;
        createFoodPellet();
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

    public void createFoodPellet(){ //Had to subtract scaled values because adding tileSize/2 wasnt centering right
        pelletShape = new Ellipse(positionVector.getVX() - padding, positionVector.getVY() - padding, sizeScalar, sizeScalar);
        pelletShape.setFillColor(Color.WHITE);
        addToCanvas();
    }

}
