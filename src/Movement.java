import edu.macalester.graphics.GraphicsObject;

public interface Movement {
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    void setShape(GraphicsObject objectShape);
    void setTileManager(TileManager tileManager);
    void setSpeed(double velVectorComponent);
    
    HitCircle getHitCircle(); //TODO: During final refactor stage extract Collider inteface
    boolean hitCircleTopCollision();
    boolean hitCircleBottomCollision();
    boolean hitCircleLeftCollision();
    boolean hitCircleRightCollision();
}
