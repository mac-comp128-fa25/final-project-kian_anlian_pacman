import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class Tile {
    private Boolean isWall = false;
    private Boolean isPellet = false;
    private Rectangle tileShape;
    private CanvasWindow canvas;
    private Vector2D positionVector;
    private static final int TILE_SIZE = 10;
    
    public Tile(Boolean isWall, Boolean isPellet, Vector2D positionVector, CanvasWindow canvas) {
        this.isWall = isWall;
        this.isPellet = isPellet;
        this.positionVector = positionVector;
        this.canvas = canvas;
        tileShape = new Rectangle (positionVector.getVX(), positionVector.getVY(), TILE_SIZE,  TILE_SIZE);

    }

    public void addWall() {
        tileShape.setFillColor(Color.BLUE);
    }

    public void addPellet() {
        Vector2D tempVector = new Vector2D(positionVector.getVX() + TILE_SIZE / 2, positionVector.getVY() + TILE_SIZE);
        FoodPellet foodPellet = new FoodPellet(tempVector, canvas);
    }


}
