
import edu.macalester.graphics.CanvasWindow;

public class PacMan extends PacManShape{
    private static final int SCALE = 5;
    private Vector2D positionVector;
    private CanvasWindow canvas;
    
    public PacMan (Vector2D positionVector, CanvasWindow canvas){//Spawn him in the middle
        super(positionVector, canvas, SCALE);  
        this.canvas = canvas;
        this.positionVector = positionVector;
    }

    public int getScale(){
        return SCALE;
    }

    public boolean intersects(Tile tile){
        Vector2D tilePositionVector = tile.getCenterVector();
        
        if (positionVector.distance(tilePositionVector) < CircleShape.RADIUS / 2){
            return true;
        }

        return false;
        
    }

    public void respawn(){
        positionVector.set(canvas.getWidth()/2 - 10, canvas.getHeight()/2);
    }
}
