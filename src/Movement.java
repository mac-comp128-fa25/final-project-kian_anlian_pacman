import edu.macalester.graphics.GraphicsObject;

public interface Movement {
    void queueUp();
    void queueDown();
    void queueLeft();
    void queueRight();
    void handleQueue();
    void setShape(GraphicsObject objectShape);
    void setTileManager(TileManager tileManager);
    void setSpeed(double velVectorComponent);
    HitCircle getHitCircle();
}
