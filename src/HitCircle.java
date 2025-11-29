import edu.macalester.graphics.CanvasWindow;

public class HitCircle extends CircleShape{
  
    public HitCircle(Vector2D positionVector, CanvasWindow canvas, Movement movement) {
        super(positionVector, canvas);
        movement.setShape(getObjectShape());
    }

    public boolean intersects(HitCircle other) {
        
        double distance = Math.hypot(getCenterX()  - other.getCenterX(), getCenterY() - other.getCenterY());
        
        if (distance <=  CircleShape.RADIUS) { //Good distance 
            return true;
        }
        else {
            return false;
        }
    }
}
