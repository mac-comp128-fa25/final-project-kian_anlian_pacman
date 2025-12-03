import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;
import java.awt.Color;

public class CircleShape implements GameObject{
    private Ellipse circleShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    public static final int RADIUS = 40;
    private Color wallColor = Tile.WALL_COLOR;

    public CircleShape(Vector2D positionVector, CanvasWindow canvas, TileManager tileManager){
        this.positionVector = positionVector;
        this.canvas = canvas;
        circleShape = new Ellipse(positionVector.getVX(), positionVector.getVY(), RADIUS, RADIUS);
        circleShape.setFillColor(Color.RED);
    }

    public boolean topTileCollision(){
        double topCenterY = circleShape.getCenter().getY() - (RADIUS / 2);
        
        GraphicsObject potentialWall = canvas.getElementAt(getCenterX(), topCenterY);
        if (potentialWall instanceof Rectangle){ 
            if (((Rectangle)potentialWall).getFillColor() == wallColor) return true;
        }
        return false;
    }

    public boolean leftTileCollision(){
        double leftCenterX = circleShape.getCenter().getX() - (RADIUS / 2);
        
        GraphicsObject potentialWall = canvas.getElementAt(leftCenterX, getCenterY());
        if (potentialWall instanceof Rectangle){ 
            if (((Rectangle)potentialWall).getFillColor() == wallColor) return true;
        }
        return false;
    }

     public boolean bottomTileCollision() {
        double bottomCenterY = circleShape.getCenter().getY() + (RADIUS / 2);
        
        GraphicsObject potentialWall = canvas.getElementAt(getCenterX(), bottomCenterY);
        if (potentialWall instanceof Rectangle){ 
            if (((Rectangle)potentialWall).getFillColor() == wallColor) return true;
        }
        return false;
    }

    public boolean rightTileCollision() {
        double rightCenterX = circleShape.getCenter().getX() + (RADIUS / 2);
        
        GraphicsObject potentialWall = canvas.getElementAt(rightCenterX, getCenterY());
        if (potentialWall instanceof Rectangle){ 
            if (((Rectangle)potentialWall).getFillColor() == wallColor) return true;
        }
        return false;
    }

    @Override
    public void addToCanvas() {
        canvas.add(circleShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(circleShape);
    }

    @Override
    public GraphicsObject getObjectShape() {
        return circleShape;
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
      return positionVector.getVY();
    }

    public double getCenterX(){
        return circleShape.getCenter().getX();
    }

    public double getCenterY(){
        return circleShape.getCenter().getY();
    }
}
