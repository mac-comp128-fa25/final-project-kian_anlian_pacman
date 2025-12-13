import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;

/**
 * @author Kian Naeimi
 * December 2025
 * 
 * This class allows us to use simple radius computations to determine when characters collide. 
 * Each character has their own (invisible) hitCircle.
 */
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

    /**
     * 
     * @param other some other character's HitCircle
     * @return True if this Character's HitCircle collided with the other's
     */
    public boolean intersects(HitCircle other) {
        double distance = Math.hypot(getCenterX()  - other.getCenterX(), getCenterY() - other.getCenterY());
        
        if (distance <= RADIUS) return true; //Good distance 
        return false;
    }
}
