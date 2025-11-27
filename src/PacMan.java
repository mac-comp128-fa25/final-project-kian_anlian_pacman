
import edu.macalester.graphics.CanvasWindow;

public class PacMan extends PacManShape{
    private static final int SCALE = 5;
    private HitCircle hitCircle;
    private Vector2D hitCirclePosVector;
    private double offsetX = 18;
    private double offsetY = 20;
    private int hitCircleScale = 40;
    
    public PacMan (Vector2D positionVector, CanvasWindow canvas){//Spawn him in the middle
        super(positionVector, canvas, SCALE);
        hitCirclePosVector = new Vector2D(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        hitCircle = new HitCircle(hitCirclePosVector, canvas, hitCircleScale);
    }

    public void addHitCircle(){
        hitCircle.addToCanvas();
    }

    public HitCircle getHitCircle(){
        return hitCircle;
    }

    public int getScale(){
        return SCALE;
    }
}
