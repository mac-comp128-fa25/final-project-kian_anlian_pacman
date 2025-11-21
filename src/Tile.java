import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class Tile implements GameObject{
    private Boolean isWall = false;
    private Boolean hasPellet = false;
    private Boolean isDefault = false;
    private Rectangle tileShape;
    private CanvasWindow canvas;
    private Vector2D positionVector;
    private Color defaultTileColor = Color.GRAY;
    private int pacManSize;
    private int tileSize;
    
    public Tile(Boolean isWall, Boolean hasPellet, Vector2D positionVector, CanvasWindow canvas, int pacManSize) {
        this.isWall = isWall;
        this.hasPellet = hasPellet;
        this.positionVector = positionVector;
        this.canvas = canvas;
        this.pacManSize = pacManSize;
        scaleToPacMan();
        tileShape = new Rectangle(positionVector.getVX(), positionVector.getVY(), tileSize,  tileSize);
        handleTileType();
    }

    public void setWall(){
        isWall = true;
    }

    public void setPellet(){
        hasPellet = true;
    }

    public void scaleToPacMan(){
        tileSize = pacManSize * 15;
        System.out.println(tileSize);
    }
    
    public int size(){
        return tileSize;
    }

    public void addWall() {
        tileShape.setFillColor(Color.BLUE);
    }

    public void addPellet() {
        Vector2D pelletPosVector = new Vector2D(tileShape.getX() + (tileSize / 2), tileShape.getY() + (tileSize / 2));
        FoodPellet foodPellet = new FoodPellet(pelletPosVector, canvas, this);
        foodPellet.addToCanvas();
    }

    public void handleTileType(){
        
        if (isWall && hasPellet || !isWall && !hasPellet){
            System.out.println("ERROR: A tile cannot be a wall and have a pellet! This tile will be in default state");
            isDefault = true;
        }

        if(isWall){
            addWall();
        }

        if (isDefault || hasPellet){
            tileShape.setFillColor(defaultTileColor);
            
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

    @Override
    public GraphicsObject getObjectShape() {
        return tileShape;
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
