import edu.macalester.graphics.GraphicsObject;

public interface Movement {
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    void setShape(GraphicsObject objectShape);
    HitCircle getHitCircle();
}
