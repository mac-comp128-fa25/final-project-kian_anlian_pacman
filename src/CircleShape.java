import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import java.awt.Color;

public class CircleShape implements GameObject{
    private Ellipse circleShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    public static final int RADIUS = 40;

    public CircleShape(Vector2D positionVector, CanvasWindow canvas){
        this.positionVector = positionVector;
        this.canvas = canvas;
        circleShape = new Ellipse(positionVector.getVX(), positionVector.getVY(), RADIUS, RADIUS);
        circleShape.setFillColor(Color.RED);
    }
    
    @Override
    public GraphicsObject getObjectShape() {
        return circleShape;
    }

    @Override
    public void addToCanvas() {
        canvas.add(circleShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(circleShape);
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
      return positionVector.getVY();
    }

    public double getCenterX(){
        return circleShape.getCenter().getX();
    }

    public double getCenterY(){
        return circleShape.getCenter().getY();
    }

    @Override
    public void handleCollisions() {
    }
}
