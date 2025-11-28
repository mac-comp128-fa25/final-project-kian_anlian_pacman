import edu.macalester.graphics.CanvasWindow;

public class HitCircle extends CircleShape{
  
    public HitCircle(Vector2D positionVector, CanvasWindow canvas, int scale, Movement movement) {
        super(positionVector, canvas, scale);
        movement.setShape(getObjectShape());
        addToCanvas();
    }
}
