import edu.macalester.graphics.GraphicsObject;
/**
 * @author Kian Naeimi
 * @author AnLian Krishnamurthy
 * December 2025
 * 
 * While somewhat bloated, this interface allows us to delegate movement requests to an object of supertype Movement,
 * such that the object doesn't need to know anything except that it has queued a movement (and called move()!)
 * We also have some other methods that are neccesary because of how multiple object's states are tied together
 * in the main class. We tried to abstract a Collider interface multiple times but all we got were class explosions..
 * so we made the decision to maintain this abstraction.
 */
public interface Movement {
    void queueUp();
    void queueDown();
    void queueLeft();
    void queueRight();

    void move();
    void setSpeed(double velVectorComponent);
    void center(GraphicsObject objectShape, GraphicsObject hitCircle, Vector2D tileCenterVector);
    
    HitCircle getHitCircle();
    void setShape(GraphicsObject objectShape);
    void setTileManager(TileManager tileManager);
}
