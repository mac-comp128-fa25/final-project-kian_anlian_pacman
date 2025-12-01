import edu.macalester.graphics.GraphicsObject;

public interface Movement {
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    void collisionBuffer(double xBuffer, double yBuffer);
    void setShape(GraphicsObject objectShape);
    HitCircle getHitCircle();
    boolean getFacingUp();
    boolean getFacingDown();
    boolean getFacingLeft();
    boolean getFacingRight();
}
