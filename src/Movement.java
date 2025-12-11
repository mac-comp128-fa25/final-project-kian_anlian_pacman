import edu.macalester.graphics.GraphicsObject;

public interface Movement {
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    void currentUp();
    void currentDown();
    void currentLeft();
    void currentRight();
    void queueUp();
    void queueDown();
    void queueLeft();
    void queueRight();
    void handleQueue();
    void setShape(GraphicsObject objectShape);
    void setTileManager(TileManager tileManager);
    void setSpeed(double velVectorComponent);
    HitCircle getHitCircle();
    HitCircle getQueueHitCircle();
}
