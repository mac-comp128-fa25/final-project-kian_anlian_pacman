
import edu.macalester.graphics.CanvasWindow;

public class PacMan extends PacManShape{
    private static final int SCALE = 5;
    
    public PacMan (Vector2D positionVector, CanvasWindow canvas){//Spawn him in the middle
        super(positionVector, canvas, SCALE);  
    }

    public int getScale(){
        return SCALE;
    }
}
