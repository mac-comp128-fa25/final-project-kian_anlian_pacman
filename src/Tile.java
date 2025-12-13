import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

/**
 * @author Kian Naeimi
 * @author AnLian Krishnamurthy
 * 
 * This class let's us actually create a game-loop. We're able to create multiple types of tiles based 
 * on the boolean values passed in the constructor. A Tile is either: A defaultTile, defaultTile w/ a FoodPellet,
 * or a WallTile.
 */
public class Tile {
    private boolean isWall, hasPellet, startedOutPellet, isDefault, explored, pelletRemoved = false;
    private FoodPellet foodPellet;
    private Rectangle tileShape;
    private Tile previous;
    private CanvasWindow canvas;
    private int pacManSize;
    private int tileSize;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final Color WALL_COLOR = Color.BLUE;
    private static final int SIZE_SCALE = 16;
    
    public Tile(Boolean isWall, Boolean hasPellet, Vector2D positionVector, CanvasWindow canvas, int pacManSize) {
        this.isWall = isWall;
        this.hasPellet = hasPellet;
        this.canvas = canvas;
        this.pacManSize = pacManSize;
        previous = null; //We use previous pointers in GhostManager to complete it's BFS algorithm, so every Tile needs one!
        scaleToPacMan(); 
        tileShape = new Rectangle(positionVector.getVX(), positionVector.getVY(), tileSize,  tileSize);
    }

    public void scaleTile(double scaleWidth, double scaleHeight){
        tileShape.setSize(tileShape.getWidth() * scaleWidth, tileShape.getHeight() * scaleHeight);
    }

    public void setDefault(){
        if (hasPellet){
            foodPellet.removeFromCanvas();
            pelletRemoved = true;
        }

        isWall = false;
        hasPellet = false;
        isDefault = true;
        handleTileType();
    }

    public void setWall(){
        isWall = true;
        isDefault = false;
        hasPellet = false;
        handleTileType();
    }

    public void setPellet(){
        hasPellet = true;
        startedOutPellet = true; //so we can add pellet back on restart
        isWall = false;
        isDefault = false;
        handleTileType();
    }

    public boolean isExplored(){
        return explored;
    }

    public void setExplored(boolean explored){
        this.explored = explored;
    }

    public Tile getPrevious(){
        return previous;
    }

    public void setPrevious(Tile previous){
        this.previous = previous;
    }

    public void colorTile(Color color){
        tileShape.setFillColor(color);
    }

    public void scaleToPacMan(){
        tileSize = pacManSize * SIZE_SCALE; 
    }
    
    public int size(){
        return tileSize;
    }

    public void addWall() {
        tileShape.setStrokeColor(WALL_COLOR);
        tileShape.setStrokeWidth(5);
    }

    public void addPellet() {
        Vector2D pelletPosVector = new Vector2D(tileShape.getX() + (tileSize / 2), tileShape.getY() + (tileSize / 2));
        foodPellet = new FoodPellet(pelletPosVector, canvas, this);
        foodPellet.addToCanvas();
    }

    public void handleTileType(){
        if (isWall && hasPellet || !isWall && !hasPellet) isDefault = true;//back off to default on edge cases

        if(isWall) addWall();

        if (isDefault || hasPellet) tileShape.setFillColor(DEFAULT_COLOR);
           
        if (!pelletRemoved) addToCanvas(); //edge case: if we don't have this safeguard the blue stroke color of wall tiles gets drawn over if next to a tile that just had its pellet removed
        
        if (hasPellet) addPellet(); 
    }

    public boolean isDefault(){
       return isDefault;
    }

    public boolean isWall(){
        return isWall;
    }

    public boolean hasFoodPellet(){
        return hasPellet;
    }

    public boolean startedOutFoodPellet(){
        return startedOutPellet;
    }

    public Vector2D getCenterVector(){ //For centering characters to enable smooth movement with our nextMove/currentMove system.
        double centerX = tileShape.getCenter().getX();
        double centerY = tileShape.getCenter().getY();

        return new Vector2D (centerX, centerY);
    }

    public void addToCanvas() {
        canvas.add(tileShape);
    }

    public GraphicsObject getObjectShape() {
        return tileShape;
    }
}
