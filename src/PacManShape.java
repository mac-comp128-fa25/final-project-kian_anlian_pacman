import java.awt.Color;
import edu.macalester.graphics.Arc;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class PacManShape implements GameObject{
    private Vector2D positionVector;
    private CanvasWindow canvas;
    private GraphicsObject pacManShape;
    private int scale;

    public PacManShape(Vector2D positionVector, CanvasWindow canvas, int scale){
        this.positionVector = positionVector;
        this.canvas = canvas;
        this.scale = scale;
        createPacMan();
    }

    /*
     *  If we run into the issue of the player accidentally running
     * into wall tiles, we can fix the issue by using a Queue data structure to 
     * hold inputted turns, and only executing (dequeuing) the turn when they're in 
     * the center of the tile they're turning onto.
     */
    public void enqueueTurn(){
    }

    public void setScale(int scale){
        this.scale = scale;
    }

    @Override
    public GraphicsObject getObjectShape() {
        return pacManShape;
    }

    @Override
    public void addToCanvas() {
        canvas.add(pacManShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(pacManShape);
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
        return positionVector.getVY();
    }

    public void createPacMan(){
        pacManShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,5, 60, 240);
        pacManShape.setScale(scale);
        ((Arc) pacManShape).setStrokeColor(Color.YELLOW);
        ((Arc) pacManShape).setStrokeWidth(5);
        addToCanvas();
    }
}
