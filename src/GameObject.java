import edu.macalester.graphics.GraphicsObject;

public interface GameObject {
    GraphicsObject getObjectShape(); 
    void addToCanvas();
    void removeFromCanvas();
    double getXPosition();
    double getYPosition();
}
