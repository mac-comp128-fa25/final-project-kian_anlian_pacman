import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Arc;

public class Ghost {
    private GraphicsObject ghostShape; //Ellipse until we figure out how to draw ghosts
    private CanvasWindow canvas;
    private Vector2D positionVector;

    public Ghost (Vector2D positionVector, CanvasWindow canvas, Color color){
        this.canvas = canvas;
        this.positionVector = positionVector;
        createGhost(color);
        addToCanvas();
    }

    public GraphicsObject getObjectShape() {
        return ghostShape;
    }

    public void addToCanvas() {
       canvas.add(ghostShape);
    }

    public void removeFromCanvas() {
        canvas.remove(ghostShape);
    }

    public void createGhost(Color color){
        ghostShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,10, 0, 180); //temp
        ((Arc) ghostShape).setStrokeColor(color); //4 ghosts: Blue, Pink, Red, Orange
        ((Arc) ghostShape).setStrokeWidth(10);
        ghostShape.setScale(3.5);
    } 
}
