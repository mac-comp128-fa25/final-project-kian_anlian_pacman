import edu.macalester.graphics.CanvasWindow;

public class HitCircle extends CircleShape{
    private Movement movement;

    public HitCircle(Vector2D positionVector, CanvasWindow canvas, int scale) {
        super(positionVector, canvas, scale);
        addToCanvas();
        movement = new StandardMovement(positionVector);
        movement.setShape(getObjectShape());
    }

    public void moveUp() {
        movement.moveUp();
    }
 
    public void moveDown() {
        movement.moveDown();
    }

    public void moveLeft() {
        movement.moveLeft();
    }
 
    public void moveRight() {
        movement.moveRight();
    }
}
