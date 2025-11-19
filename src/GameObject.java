import edu.macalester.graphics.GraphicsObject;

public interface GameObject {
    GraphicsObject getObjectShape(); //For managers (FoodPelletManager, GhostManager, etc)
    void addToCanvas();
    void removeFromCanvas();
    double getXPosition();
    double getYPosition();
    void handleCollisions();
}
