import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Arc;

public class Ghost implements GameObject{
    private GraphicsObject ghostShape; //Ellipse until we figure out how to draw ghosts
    private CanvasWindow canvas;
    private Vector2D positionVector;

    public Ghost (Vector2D positionVector, CanvasWindow canvas, Color color){
        this.canvas = canvas;
        this.positionVector = positionVector;
        createGhost(color);
        addToCanvas();
    }

    @Override
    public GraphicsObject getObjectShape() {
        return ghostShape;
    }

    @Override
    public void addToCanvas() {
       canvas.add(ghostShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(ghostShape);
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
        return positionVector.getVY();
    }

    public void createGhost(Color color){
        ghostShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,10, 0, 180); //temp
        ((Arc) ghostShape).setStrokeColor(color); //4 ghosts: Blue, Pink, Red, Orange
        ((Arc) ghostShape).setStrokeWidth(10);
        ghostShape.setScale(3.5);
    } 
}
