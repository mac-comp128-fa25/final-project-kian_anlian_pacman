import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;

public class HitCircle{
    private Ellipse circleShape;
    public static final int RADIUS = 40;

    public HitCircle(Vector2D positionVector, CanvasWindow canvas, Movement movement, TileManager tileManager) {
        movement.setShape(circleShape);
        circleShape = new Ellipse(positionVector.getVX(), positionVector.getVY(), RADIUS, RADIUS);
        circleShape.setFillColor(Color.RED);
    }

    public GraphicsObject getObjectShape() {
        return circleShape;
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
