import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class FoodPellet implements GameObject{
    private Ellipse pelletShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private final int paddingScalar = 6;
    private final int sizeScaler = 3;
    private final int padding;
    private final int size;

    public FoodPellet(Vector2D positionVector, CanvasWindow canvas, Tile tile){
        this.positionVector = positionVector;
        this.canvas = canvas;
        padding = tile.size() / paddingScalar;
        size = tile.size() / sizeScaler;
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

    public void createFoodPellet(){ 
        pelletShape = new Ellipse(positionVector.getVX() - padding, positionVector.getVY() - padding, size, size);
        pelletShape.setFillColor(new Color(222, 161, 133));
        addToCanvas();
    }
}
