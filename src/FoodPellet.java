import edu.macalester.graphics.Ellipse;
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
/**
 * @author Kian Naeimi 
 * @author AnLian Krishnamurthy 
 * December 2025
 *  
 * This class is responsible for creating the FoodPellet object.
 * Added in the center of a Tile and centered using a padding constant.
 * In the DefaultMap file, the number 1 occupying some [column][row] position in the 
 * matrix adds a FoodPellet to that Tile. 
 */
public class FoodPellet{
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

    public void addToCanvas() {
        canvas.add(pelletShape);
    }

    public void removeFromCanvas() {
        canvas.remove(pelletShape);
    }

    public void createFoodPellet(){//Padding helps center the pellet in it's Tile.
        pelletShape = new Ellipse(positionVector.getVX() - padding, positionVector.getVY() - padding, size, size);
        pelletShape.setFillColor(new Color(222, 161, 133));
        addToCanvas();
    }
}
