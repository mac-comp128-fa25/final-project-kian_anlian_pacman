import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class Tile implements GameObject{
    private boolean isWall = false;
    private boolean hasPellet = false;
    private boolean isDefault = false;
    private boolean explored = false;
    private Rectangle tileShape;
    private Tile previous;
    private CanvasWindow canvas;
    private Vector2D positionVector;
    public static final Color DEFAULT_COLOR = Color.DARK_GRAY;
    public static final Color WALL_COLOR = Color.LIGHT_GRAY;
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
        handleTileType();
    }

    public void scaleTile(double scaleWidth, double scaleHeight){
        tileShape.setSize(tileShape.getWidth() * scaleWidth, tileShape.getHeight() * scaleHeight);
    }

    public void setDefault(){
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
        tileShape.setFillColor(WALL_COLOR);
    }

    public void addPellet() {
        Vector2D pelletPosVector = new Vector2D(tileShape.getX() + (tileSize / 2), tileShape.getY() + (tileSize / 2));
        FoodPellet foodPellet = new FoodPellet(pelletPosVector, canvas, this);
        foodPellet.addToCanvas();
    }

    public void handleTileType(){
        if (isWall && hasPellet || !isWall && !hasPellet){ //back off to default on edge cases
            isDefault = true;
        }

        if(isWall){
            addWall();
        }

        if (isDefault || hasPellet){
            tileShape.setFillColor(DEFAULT_COLOR);
        }

        addToCanvas();
        
        if (hasPellet){
            addPellet();
        }
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
