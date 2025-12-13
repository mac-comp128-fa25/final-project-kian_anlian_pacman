import edu.macalester.graphics.GraphicsObject;

public interface Movement {
    void queueUp();
    void queueDown();
    void queueLeft();
    void queueRight();
    void move();
    void setSpeed(double velVectorComponent);
    void center(GraphicsObject objectShape, GraphicsObject hitCircle, Vector2D tileCenterVector);

    //Are these related?
    HitCircle getHitCircle();
    void setShape(GraphicsObject objectShape);
    void setTileManager(TileManager tileManager);
}
