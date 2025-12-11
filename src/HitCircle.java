import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

public class HitCircle implements GameObject{
    private Ellipse circleShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    public static final int RADIUS = 40;
    private int radius;
    private Color wallColor = Tile.WALL_COLOR;

    public HitCircle(Vector2D positionVector, CanvasWindow canvas, Movement movement, TileManager tileManager, int radius) {
        this.positionVector = positionVector;
        this.canvas = canvas;
        this.radius = radius;
        movement.setShape(circleShape);
        circleShape = new Ellipse(positionVector.getVX(), positionVector.getVY(), radius, radius);
        circleShape.setFillColor(Color.RED);
    }

    public boolean topTileCollision(){
        double topCenterY = circleShape.getCenter().getY() - (radius / 2);
        
        GraphicsObject potentialWall = canvas.getElementAt(getCenterX(), topCenterY);
        if (potentialWall instanceof Rectangle){ 
            if (((Rectangle)potentialWall).getFillColor() == wallColor) return true;
        }
        return false;
    }

    public boolean leftTileCollision(){
        double leftCenterX = circleShape.getCenter().getX() - (radius / 2);
        
        GraphicsObject potentialWall = canvas.getElementAt(leftCenterX, getCenterY());
        if (potentialWall instanceof Rectangle){ 
            if (((Rectangle)potentialWall).getFillColor() == wallColor) return true;
        }
        return false;
    }

     public boolean bottomTileCollision() {
        double bottomCenterY = circleShape.getCenter().getY() + (radius / 2);
        
        GraphicsObject potentialWall = canvas.getElementAt(getCenterX(), bottomCenterY);
        if (potentialWall instanceof Rectangle){ 
            if (((Rectangle)potentialWall).getFillColor() == wallColor) return true;
        }
        return false;
    }

    public boolean rightTileCollision() {
        double rightCenterX = circleShape.getCenter().getX() + (radius / 2);
        
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

    public boolean intersects(HitCircle other) {
        
        double distance = Math.hypot(getCenterX()  - other.getCenterX(), getCenterY() - other.getCenterY());
        
        if (distance <= RADIUS) { //Good distance 
            return true;
        }
        else {
            return false;
        }
    }
}
