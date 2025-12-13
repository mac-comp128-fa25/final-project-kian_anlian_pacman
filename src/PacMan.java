import java.awt.Color;
import edu.macalester.graphics.Arc;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class PacMan {
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private GraphicsObject pacManShape;
    private int scale;

    public PacMan(Vector2D positionVector, CanvasWindow canvas, int scale){
        this.positionVector = positionVector;
        this.canvas = canvas;
        this.scale = scale;
        createPacMan();
    }

    public int getScale(){
        return scale;
    }

    public GraphicsObject getObjectShape() {
        return pacManShape;
    }

    public void addToCanvas() {
        canvas.add(pacManShape);
    }

    public void removeFromCanvas() {
        canvas.remove(pacManShape);
    }
    
    public void setPositionVector(Vector2D positionVector){
        this.positionVector = positionVector;
    }

    public boolean intersects(Tile tile){
        Vector2D tilePositionVector = tile.getCenterVector();
        
        if (positionVector.distance(tilePositionVector) < HitCircle.RADIUS / 2){
            return true;
        }

        return false;
    }

    public void createPacMan(){
        pacManShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,5, 60, 240);
        pacManShape.setScale(scale);
        ((Arc) pacManShape).setStrokeColor(Color.YELLOW);
        ((Arc) pacManShape).setStrokeWidth(5);
        addToCanvas();
    }
}
