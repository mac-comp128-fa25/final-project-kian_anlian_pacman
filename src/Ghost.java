import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Arc;

/**
 * @author Kian Naeimi 
 * December 2025
 *  
 * We use this class to create our favorite ghosts: Pinky, Blinky, Inky, and Clyde
 * (Pink, Red, Blue, Orange respectively)!
 * We make use of an Arc object to create the signature silouhette of each ghost.
 * To stay minimalistic (and also because this is a small project!), we're not going
 * to worry about animating eyes or any other feature requiring any form of 
 * artistic skill.
 */
public class Ghost {
    private GraphicsObject ghostShape; 
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
    
    /*
     * Very similar to Pac-Man (he's an Arc too), but here we make exactly half of a circle and mess with the scale
     * to get a decent looking model. 
     */
    public void createGhost(Color color){
        ghostShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,10, 0, 180); 
        ((Arc) ghostShape).setStrokeColor(color);
        ((Arc) ghostShape).setStrokeWidth(10);
        ghostShape.setScale(3.5);
    } 
}
