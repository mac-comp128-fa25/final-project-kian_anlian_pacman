import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class FoodPellet implements GameObject{
    private Ellipse pelletShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private static final int PELLET_SIZE = 20;
    private Tile tile;

    /*
     * TODO: Create a HashMap where the key is some tile at (x,y) and the value is 
     * an object of type FoodPellet.
     */

    public FoodPellet(Vector2D positionVector, CanvasWindow canvas, Tile tile){
        this.positionVector = positionVector;
        this.canvas = canvas;
        this.tile = tile;
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
    public double getXPosition() { //returning centerX and centerY for proper positioning
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() { 
        return positionVector.getVY();
    }

    @Override
    public void handleCollisions() {
    }

    public void createFoodPellet(){
        
        pelletShape = new Ellipse(positionVector.getVX() - tile.size() / 6, positionVector.getVY() - tile.size() /6, tile.size()/3, tile.size()/3);
        // pelletShape.setCenter(positionVector.getVX(), positionVector.getVY());
        pelletShape.setFillColor(Color.WHITE);
        addToCanvas();
    }

}
