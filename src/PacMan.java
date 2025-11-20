
import edu.macalester.graphics.CanvasWindow;

public class PacMan extends PacManShape{
    private Movement movement;
    private static final int SCALE = 5;
    
    public PacMan (Vector2D positionVector, CanvasWindow canvas, Movement movement){//Spawn him in the middle
        super(positionVector, canvas, SCALE);
        this.movement = movement;
    }

    public void moveUp() {
        movement.moveUp();
    }
 
    public void moveDown() {
        movement.moveDown();
    }

    public void moveLeft() {
        movement.moveLeft();
    }
 
    public void moveRight() {
        movement.moveRight();
    }

    public int getScale(){
        return SCALE;
    }
}
