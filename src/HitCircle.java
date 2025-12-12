import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;

public class HitCircle implements GameObject{
    private Ellipse circleShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    public static final int RADIUS = 40;

    public HitCircle(Vector2D positionVector, CanvasWindow canvas, Movement movement, TileManager tileManager) {
        this.positionVector = positionVector;
        this.canvas = canvas;
        movement.setShape(circleShape);
        circleShape = new Ellipse(positionVector.getVX(), positionVector.getVY(), RADIUS, RADIUS);
        circleShape.setFillColor(Color.RED);
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
