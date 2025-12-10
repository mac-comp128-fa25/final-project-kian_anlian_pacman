import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class StandardMovement implements Movement{
    private Vector2D positionVector;
    private TileManager tileManager;
    private Vector2D velocityVector = new Vector2D(0,0);
    private double velVectorComponent = 2;
    private GraphicsObject objectShape; 
    private GraphicsObject hitCircleShape;
    private HitCircle hitCircle;
    private Vector2D hitCirclePosVector;
    private double offsetX = 18;
    private double offsetY = 20;
    // public enum Move{UP, DOWN, LEFT, RIGHT} //TODO: Figure out how to center characters here... and queue movements...
    // private Move nextMove = null;
    // private Move currentMove = null;

    public StandardMovement(Vector2D positionVector, CanvasWindow canvas) {
        this.positionVector = positionVector;
        hitCirclePosVector = new Vector2D(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        hitCircle = new HitCircle(hitCirclePosVector, canvas, this, tileManager);
        hitCircleShape = hitCircle.getObjectShape();
    }

    public void setSpeed(double velVectorComponent){
        this.velVectorComponent = velVectorComponent;
    }

    public void setTileManager(TileManager tileManager){
        this.tileManager = tileManager;
    }

    public Tile getCurrentTile(){
        return tileManager.getCurrentTile(hitCircle);
    }

    public Vector2D getTileCenter(){
        return getCurrentTile().getCenterVector();
    }

    public double getTileX(){
        return getCurrentTile().getXPosition();
    }

    public double getTileY(){
        return getCurrentTile().getYPosition();
    }

    public boolean hitCircleTopCollision(){
        if (hitCircle.topTileCollision()) return true;
        return false;
    }

    public boolean hitCircleBottomCollision(){
        if (hitCircle.bottomTileCollision()) return true;
        return false;
    }

    public boolean hitCircleLeftCollision(){
        if (hitCircle.leftTileCollision()) return true;
        return false;
    }

    public boolean hitCircleRightCollision(){
        if (hitCircle.rightTileCollision()) return true;
        return false;
    }

    public HitCircle getHitCircle() {
        return hitCircle;
    }

    public GraphicsObject getShape(){
        return objectShape;
    }
    
    public void setShape(GraphicsObject objectShape){
        this.objectShape = objectShape;
    }

    public void moveUp() {
        velocityVector.set(0,velVectorComponent);
        positionVector.add(velocityVector.getVX(),velocityVector.getVY()); //move 0 units right and 5 units up every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }
    

    public void moveDown() {
        velocityVector.set(0,velVectorComponent);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); //move 0 units right and 5 units down every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void moveLeft() {
        velocityVector.set(velVectorComponent,0);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); 
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void moveRight() {
        velocityVector.set(velVectorComponent,0);
        positionVector.add(velocityVector.getVX(), velocityVector.getVY());
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }
}
