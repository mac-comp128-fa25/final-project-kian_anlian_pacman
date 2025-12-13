import java.awt.Color;
import edu.macalester.graphics.Arc;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

/**
 * @author Kian Naeimi
 * @author AnLian Krishnamurthy
 * December 2025
 * 
 * This class let's us create our Pac-Man! Due to time-constraints (and lack of artistic talent), we chose not
 * to implement an opening/closing mouth. The only interesting method here is our intersects() implementation.
 * In this instance, we want a pretty rough approximation of Pac-Man's distance from a Tile's center.
 * Essentially, we use this method to remove FoodPellets from Tiles (that have them, obviously) once Pac-Man's
 * at a distance that makes sense visually. This way, player's can quickly run through pellets without worrying
 * about a HitCircle! 
 * 
 */
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

    /**
     * 
     * @param tile a tile that has a FoodPellet.
     * @return True if Pac-Man's positionVector has gotten reasonably close to the Tile's centerVector.
     */
    public boolean intersects(Tile tile){
        Vector2D tilePositionVector = tile.getCenterVector();
        
        if (positionVector.distance(tilePositionVector) < HitCircle.RADIUS / 2) return true; 
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
