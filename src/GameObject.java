import edu.macalester.graphics.GraphicsObject;

public interface GameObject {
    public void createObject();
    public GraphicsObject getObjectShape(); //For managers (FoodPelletManager, PacManManager, etc)
    public void addToCanvas();
    public void removeFromCanvas();
    public double getCenterX();
    public double getCenterY();
}
