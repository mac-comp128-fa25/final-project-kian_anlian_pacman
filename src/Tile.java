import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class Tile implements GameObject{
    private boolean isWall = false;
    private boolean hasPellet = false;
    private boolean startedOutPellet = false;
    private boolean isDefault = false;
    private boolean explored = false;
    private boolean pelletRemoved = false;
    private FoodPellet foodPellet;
    private Rectangle tileShape;
    private Tile previous;
    private CanvasWindow canvas;
    private Vector2D positionVector;
    public static final Color DEFAULT_COLOR = Color.BLACK;
    public static final Color WALL_COLOR = Color.BLUE;
    private int pacManSize;
    private int tileSize;
    private static final int SIZE_SCALE = 16;
    
    public Tile(Boolean isWall, Boolean hasPellet, Vector2D positionVector, CanvasWindow canvas, int pacManSize) {
        this.isWall = isWall;
        this.hasPellet = hasPellet;
        this.positionVector = positionVector;
        this.canvas = canvas;
        this.pacManSize = pacManSize;
        previous = null;
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

    public Vector2D getCenterVector(){
        double centerX = tileShape.getCenter().getX();
        double centerY = tileShape.getCenter().getY();

        return new Vector2D (centerX, centerY);
    }

    @Override
    public void addToCanvas() {
        canvas.add(tileShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(tileShape);
    }

     @Override
    public GraphicsObject getObjectShape() {
        return tileShape;
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
        return positionVector.getVY();
    }
}
