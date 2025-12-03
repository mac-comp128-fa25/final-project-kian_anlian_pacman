
import edu.macalester.graphics.CanvasWindow;

public class PacMan extends PacManShape{
    private static final int SCALE = 5;
    private Vector2D positionVector;
    private Vector2D respawnPositionVector;
    
    public PacMan (Vector2D positionVector, CanvasWindow canvas){//Spawn him in the middle
        super(positionVector, canvas, SCALE);  
        this.positionVector = positionVector;
        respawnPositionVector = new Vector2D(canvas.getWidth()/2 - 10, canvas.getHeight()/2.3);
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
        positionVector.set(respawnPositionVector);
    }
}
