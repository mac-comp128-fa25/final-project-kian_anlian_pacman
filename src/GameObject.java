import edu.macalester.graphics.GraphicsObject;

public interface GameObject {
    GraphicsObject getObjectShape(); //For managers (FoodPelletManager, PacManManager, etc)
    void addToCanvas();
    void removeFromCanvas();
    double getXPosition();
    double getYPosition();
    void handleCollisions();
}
