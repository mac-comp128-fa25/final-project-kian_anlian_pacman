import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsObject;
import java.awt.Color;

public class CircleShape implements GameObject{
    private Ellipse circleShape;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private int scale;

    public CircleShape(Vector2D positionVector, CanvasWindow canvas, int scale){
        this.positionVector = positionVector;
        this.canvas = canvas;
        this.scale = scale;
        circleShape = new Ellipse(positionVector.getVX(), positionVector.getVY(), scale, scale);
        circleShape.setFillColor(Color.RED);
        addToCanvas();
    }
    
    @Override
    public GraphicsObject getObjectShape() {
        return circleShape;
    }

    public int getScale(){
        return scale;
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

    @Override
    public void handleCollisions() {
    }

}
